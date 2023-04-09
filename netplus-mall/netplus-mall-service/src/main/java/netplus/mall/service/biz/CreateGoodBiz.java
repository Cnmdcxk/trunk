package netplus.mall.service.biz;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import netplus.component.entity.enums.YesNoEnum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.mall.api.enums.GoodsStatusEnum;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.api.pojo.ygmalluser.Tbmqq435;
import netplus.mall.api.request.good.BatchInsertGoodRequest;
import netplus.mall.api.vo.Tbmqq435Vo;
import netplus.mall.service.dao.Tbmqq430Mapper;
import netplus.mall.service.dao.Tbmqq435Mapper;
import netplus.utils.date.NowDate;
import netplus.utils.pager.Pager;
import netplus.utils.uuid.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CreateGoodBiz extends ServiceImpl<Tbmqq430Mapper, Tbmqq430> {

    @Autowired
    private Tbmqq430Mapper tbmqq430Mapper;

    @Autowired
    private Tbmqq435Mapper tbmqq435Mapper;

    @Autowired
    private FutureGoodBiz futureGoodBiz;

    @Transactional(rollbackFor = Exception.class)
    public void batchInsert (BatchInsertGoodRequest request) {

        //默认一个请求只会有一个常协合同
        List<Tbmqq430> reqList = request.getTbmqq430List();
        String ponoPk = reqList.get(0).getPonopk();
        String supplierNo = reqList.get(0).getSupplierno();

        //先将中间表数据移除
        futureGoodBiz.deleteFutureGoodByPoNoPk(ponoPk);

        //当前协议物料id
        Set<String> matrlIdSet = reqList.stream().map( t -> t.getMatrlid()).collect(Collectors.toSet());

        List<Tbmqq430> supplierOtherPonoGoodList  = tbmqq430Mapper.getSupplierOtherPonoGood(ponoPk, supplierNo);
        Map<String, String> supplierOtherPonoGoodMap = supplierOtherPonoGoodList.stream().collect(Collectors.toMap(Tbmqq430::getMatrlid, t -> t.getPono()));

        List<String> existOtherPonoGoodList = new ArrayList();

        for (String m: matrlIdSet) {

            if (supplierOtherPonoGoodMap.keySet().contains(m)) {
                existOtherPonoGoodList.add(m);
            }

        }

        if (existOtherPonoGoodList.size() > 0) {
            throw new NetPlusException(String.format("协议pk:%s中的物料id：%s, 已存在其他协议中", ponoPk, new Gson().toJson(existOtherPonoGoodList)));
        }

        List<Tbmqq430> existGoodList = tbmqq430Mapper.getGoodByPonoPk(ponoPk);
        Map<String, Tbmqq430> existGoodMap = existGoodList.stream().collect(Collectors.toMap(Tbmqq430::getMatrlid, t -> t));


        List<Tbmqq430> insertList = new ArrayList();
        List<Tbmqq430> updateList = new ArrayList();
        List<String> deleteList = new ArrayList();
        NowDate nowDate = new NowDate();

        for (Tbmqq430 t: reqList) {

            t.setUpdatedate(nowDate.getDateStr());
            t.setUpdatetime(nowDate.getTimeStr());
            t.setUpdateuser(SysCodeEnum.CK.code);

            if (existGoodMap.keySet().contains(t.getMatrlid())) {

                t.setGoodid(existGoodMap.get(t.getMatrlid()).getGoodid());
                updateList.add(t);

            }else{

                t.setGoodid(UuidUtil.getUuid());
                t.setCreatedate(nowDate.getDateStr());
                t.setCreatetime(nowDate.getTimeStr());
                t.setCreateuser(SysCodeEnum.CK.code);
                t.setSourcefrom(SysCodeEnum.CK.code);
                t.setDeleted(YesNoEnum.No.getValue());
                t.setIstaxexception("ZC");
                t.setStatus(GoodsStatusEnum.status_10.getCode());

                insertList.add(t);

            }
        }

        for (String k: existGoodMap.keySet()) {

            if (!matrlIdSet.contains(k)) {
                deleteList.add(existGoodMap.get(k).getGoodid());
            }
        }

        //添加商品信息
        if (insertList.size() > 0) {

            Pager<Tbmqq430> tbmqq430Pager = new Pager(insertList, 100);
            for (int i=1; i<=tbmqq430Pager.getTotalPage(); i++) {
                tbmqq430Mapper.batchInsert(tbmqq430Pager.getPageData(i));
            }

            Map<String, String> matrlIdAndGoodIdMap = reqList
                    .stream()
                    .collect(Collectors.toMap(Tbmqq430::getMatrlid, t -> t.getGoodid()));

            List<Tbmqq435> insertTbmqq435List = new ArrayList();

            for (Tbmqq435Vo tbmqq435Vo: request.getTbmqq435List()) {

                if (!existGoodMap.keySet().contains(tbmqq435Vo.getMatrlid())) {
                    Tbmqq435 tbmqq435 = new Tbmqq435();
                    BeanUtils.copyProperties(tbmqq435Vo, tbmqq435);
                    tbmqq435.setGoodid(matrlIdAndGoodIdMap.get(tbmqq435Vo.getMatrlid()));
                    insertTbmqq435List.add(tbmqq435);
                }

            }

            if (insertTbmqq435List.size() > 0) {
                Pager<Tbmqq435> tbmqq435Pager = new Pager(insertTbmqq435List, 100);
                for (int i=1; i<=tbmqq435Pager.getTotalPage(); i++) {
                    tbmqq435Mapper.batchInsert(tbmqq435Pager.getPageData(i));
                }
            }
        }

        //修改商品信息
        if (updateList.size() > 0) {

            for (Tbmqq430 t: updateList) {
                int count = tbmqq430Mapper.updateByPrimaryKeySelective(t);
                if (count != 1) {
                    throw new NetPlusException("商品更新失败");
                }
            }
        }

        //删除商品信息
        if (deleteList.size() > 0) {

            Map<String, Object> delMap = new HashMap();
            delMap.put("supplierNo", supplierNo);
            delMap.put("goodIdList", deleteList);

            tbmqq435Mapper.delGoodPic(delMap);

            int count = tbmqq430Mapper.delGoodByGoodId(delMap);
            if (count != deleteList.size()) {
                throw new NetPlusException("商品删除失败");
            }
        }
    }
}
