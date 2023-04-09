package netplus.mall.service.biz;

import netplus.component.entity.data.KeyValue;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.enums.OneOrZeroEnum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.BaseResponse;
import netplus.component.entity.request.RequestBase;
import netplus.joint.erp.api.request.out.JK0010.JK0010Request;
import netplus.joint.erp.api.request.out.JK0010.JK0010SubRequest;
import netplus.joint.erp.api.response.out.JK0010.JK0010Response;
import netplus.joint.erp.api.response.out.JK0010.JK0010SubResponse;
import netplus.joint.erp.api.rest.ErpOutRest;
import netplus.mall.api.request.goodsInventory.GoodsInventoryPageRequest;
import netplus.mall.api.request.goodsInventory.MallGoodsInventoryPageRequest;
import netplus.mall.api.vo.goodsInventory.GoodsInventoryPageVo;
import netplus.mall.api.vo.goodsInventory.MallGoodsInventoryPageVo;
import netplus.mall.api.vo.order.Tbmqq441Vo;
import netplus.utils.date.NowDate;
import netplus.utils.uuid.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsInventoryBiz {

    @Autowired
    private MallBiz mallBiz;

    @Autowired
    private OrderBiz orderBiz;

    @Autowired
    private ErpOutRest erpOutRest;

    public PageBean<GoodsInventoryPageVo> getPage(GoodsInventoryPageRequest request) {
        NowDate nowDate = new NowDate();
        //分页查询物料信息
        List<KeyValue> goodsMatrlInfo = mallBiz.getGoodsMatrlInfo(request);

        //物料总数量
        int matrlCount = mallBiz.getGoodsMatrlCount(request);

        //获取到所有物料ID
        List<String> matrlIdList = goodsMatrlInfo.stream().map(KeyValue::getKey).collect(Collectors.toList());

        //根据物料ID获取商城库存
        Map<String, BigDecimal> mallInventoryByMatrlId = orderBiz.getMallInventoryByMatrlId(matrlIdList);

        //根据物料ID获取仓库库存
        Map<String, JK0010SubResponse> warehouseInventoryByMatrlId = getWarehouseInventoryByMatrlId(matrlIdList);

        //组装数据
        List<GoodsInventoryPageVo> pageVoList = goodsMatrlInfo.stream().map(item -> {
            String matrlId = item.getKey();
            GoodsInventoryPageVo vo = new GoodsInventoryPageVo();
            vo.setMatrlId(matrlId);
            vo.setMatrlTm(item.getValue());

            BigDecimal mallInventory = Optional.ofNullable(mallInventoryByMatrlId.get(matrlId)).orElse(BigDecimal.ZERO);
            vo.setMallInventory(mallInventory);

            BigDecimal warehouseInventory = Optional.ofNullable(warehouseInventoryByMatrlId.get(matrlId)).map(JK0010SubResponse::getKcsl).orElse(BigDecimal.ZERO);
            vo.setWarehouseInventory(warehouseInventory);

            BigDecimal warehouseInventoryMax = Optional.ofNullable(warehouseInventoryByMatrlId.get(matrlId)).map(JK0010SubResponse::getKcslsx).orElse(BigDecimal.ZERO);
            vo.setWarehouseInventoryMax(warehouseInventoryMax);

            vo.setTotalInventory(mallInventory.add(warehouseInventory));
            return vo;
        }).collect(Collectors.toList());

        PageBean<GoodsInventoryPageVo> page = new PageBean<>();
        page.setItems(pageVoList);
        page.setTotalCount(matrlCount);
        page.addResultMap("searchTime",nowDate.getDateTimeStr2());

        return page;
    }

    private Map<String, JK0010SubResponse> getWarehouseInventoryByMatrlId(List<String> matrlIdList){
        List<JK0010SubRequest> reqData = matrlIdList.stream().map(matrlId -> {
            JK0010SubRequest req = new JK0010SubRequest();
            req.setJhsl(BigDecimal.ZERO);
            req.setWzmcbm_pk(matrlId);
            return req;
        }).collect(Collectors.toList());
        JK0010Request req = new JK0010Request();
        req.setData(reqData);

        BaseRequest<JK0010Request> request = new BaseRequest<>();
        request.setReqId(UuidUtil.getUuid());
        request.setReqTime(String.valueOf(System.currentTimeMillis()));
        request.setReqData(req);

        BaseResponse<JK0010Response> response = erpOutRest.JK0010(request);
        if (OneOrZeroEnum.Zero.getValue().equals(response.getStatus())) {
            throw new NetPlusException(response.getMessage());
        }

        List<JK0010SubResponse> data = response.getRespData().getData();;
        Map<String, JK0010SubResponse> result = data.stream().collect(Collectors.toMap(JK0010SubResponse::getWzmcbm_pk, e -> e, (e1, e2) -> e2));
        return result;
    }

    public PageBean<MallGoodsInventoryPageVo> getMallGoodsInventoryDetailPage(MallGoodsInventoryPageRequest request) {
        PageBean<Tbmqq441Vo> orderPageBen = orderBiz.getMallGoodsInventoryDetailByMatrlId(request);

        List<MallGoodsInventoryPageVo> items = orderPageBen.getItems().stream().map(item -> {
            MallGoodsInventoryPageVo vo = new MallGoodsInventoryPageVo();
            vo.setOrderNo(item.getOrderno());
            vo.setOrderDetailNo(item.getOrderdetailno());
            vo.setMatrlTm(item.getMatrltm());
            vo.setQty(item.getQty());
            vo.setCreateDate(item.getCreatedate());
            vo.setCreateTime(item.getCreatetime());
            vo.setStatusName(item.getStatusName());
            vo.setSupplierNameStr(String.format("%s-%s",item.getSupplierno(),item.getSuppliername()));
            return vo;
        }).collect(Collectors.toList());

        PageBean<MallGoodsInventoryPageVo> page = new PageBean<>();
        page.setItems(items);
        page.setTotalCount(orderPageBen.getTotalCount());
        return page;
    }
}
