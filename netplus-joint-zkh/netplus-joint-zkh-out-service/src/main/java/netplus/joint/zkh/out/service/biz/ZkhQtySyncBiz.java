package netplus.joint.zkh.out.service.biz;

import com.google.gson.Gson;
import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.joint.zkh.api.pojo.GoodsInventoryVo;
import netplus.mall.api.request.good.GetGoodBySupplierNoRequest;
import netplus.mall.api.request.good.UpdateZkhGoodQtyRequest;
import netplus.mall.api.rest.BasicDataRest;
import netplus.mall.api.rest.GoodsRest;
import netplus.mall.api.vo.GoodsVo;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.rest.ServiceConfigRest;
import netplus.utils.date.NowDate;
import netplus.utils.json.JsonUtil;
import netplus.utils.number.NumberUtil;
import netplus.utils.object.ObjectUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ZkhQtySyncBiz {

    private static Log logger = LogFactory.getLog(ZkhQtySyncBiz.class);

    public static final Gson gson = JsonUtil.JsonInstance;

    @Autowired
    ZkhOutBiz zkhOutBiz;

    @Autowired
    BasicDataRest basicDataRest;

    @Autowired
    GoodsRest goodsRest;


    @Autowired
    ServiceConfigRest serviceConfigRest;


    public void qtySync () {


        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_OUT_SUPPLIER.code);
        String supplierNo = tbmqq461.getVal();
        NowDate nowDate = new NowDate();

        GetGoodBySupplierNoRequest getGoodBySupplierNoRequest = new GetGoodBySupplierNoRequest();
        getGoodBySupplierNoRequest.setSupplierNo(supplierNo);
        List<GoodsVo> goodsVoList = goodsRest.getGoodBySupplierNo(getGoodBySupplierNoRequest);

        Map<String, String> skuAndGoodIdMap = goodsVoList.stream().collect(Collectors.toMap(GoodsVo::getGoodno, g -> g.getGoodid()));
        if (ObjectUtil.nonEmpty(goodsVoList)) {

            List<String> skuList = new ArrayList(skuAndGoodIdMap.keySet());

            int pageSize = 20;
            int totalPage = skuList.size() / pageSize;
            int remain = skuList.size() % pageSize;
            if (remain > 0) {
                totalPage  = totalPage + 1;
            }

            for (int i = 1; i <= totalPage; i++) {

                int success = 0;
                int start = (i - 1) * pageSize;
                int end = i * pageSize;

                if (end > skuList.size()) {
                    end = skuList.size();
                }

                int totalCount = end - start;
                List<String> subSkuList = skuList.subList(start, end);

                logger.info(String.format("震坤行商品库存同步第%d页: 共%d个", i, totalCount));

                List<GoodsInventoryVo> goodsInventoryVoList = zkhOutBiz.getProductInventory(subSkuList);
                if (ObjectUtil.nonEmpty(goodsInventoryVoList)) {

                    Map<String, GoodsInventoryVo> goodsInventoryVoMap = goodsInventoryVoList
                            .stream()
                            .collect(Collectors.toMap(GoodsInventoryVo::getSkuId, g -> g));

                    for (String sku: goodsInventoryVoMap.keySet()) {

                        String goodId = skuAndGoodIdMap.get(sku);
                        GoodsInventoryVo goodsInventoryVo = goodsInventoryVoMap.get(sku);

                        UpdateZkhGoodQtyRequest updateZkhGoodQtyRequest = new UpdateZkhGoodQtyRequest();
                        updateZkhGoodQtyRequest.setGoodId(goodId);
                        updateZkhGoodQtyRequest.setSupplierNo(supplierNo);
                        updateZkhGoodQtyRequest.setQty(NumberUtil.i2b(goodsInventoryVo.getRemainNum()));
                        updateZkhGoodQtyRequest.setUpdateDate(nowDate.getDateStr());
                        updateZkhGoodQtyRequest.setUpdateTime(nowDate.getTimeStr());
                        updateZkhGoodQtyRequest.setUpdateUser(SysCodeEnum.ZKH.code);

                        try {
                            int updateCount = goodsRest.updateZkhGoodQty(updateZkhGoodQtyRequest);
                            success = success + updateCount;
                        }catch (Exception e) {
                            logger.info(String.format("sku: %s库存同步失败", sku));
                        }
                    }

                    logger.info(String.format("震坤行商品图片库存第%d页: 共%d个, 成功%d个, 失败%d个", i, totalCount, success, totalCount - success));

                }else{

                    logger.info(String.format("震坤行商品图片库存第%d页: 查询不到数据", i));

                }
            }

        }else{
            logger.info(String.format("暂无震坤行商品需要同步"));
        }
    }
}
