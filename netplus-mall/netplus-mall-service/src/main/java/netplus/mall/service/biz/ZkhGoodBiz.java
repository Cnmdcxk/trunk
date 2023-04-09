package netplus.mall.service.biz;

import netplus.mall.api.pojo.ygmalluser.Tbmqq432;
import netplus.mall.api.request.good.*;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.service.dao.Tbmqq430Mapper;
import netplus.mall.service.dao.Tbmqq432Mapper;
import netplus.mall.service.dao.Tbmqq435Mapper;
import netplus.mall.service.dao.Tbmqq436Mapper;
import netplus.utils.object.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class ZkhGoodBiz {

    @Autowired
    private Tbmqq430Mapper tbmqq430Mapper;

    @Autowired
    private Tbmqq432Mapper tbmqq432Mapper;

    @Autowired
    private Tbmqq436Mapper tbmqq436Mapper;

    @Autowired
    private Tbmqq435Mapper tbmqq435Mapper;

    @Transactional(rollbackFor = Exception.class)
    public int updateZkhGoodPrice (UpdateZkhGoodPriceRequest request) {

        int updateCount = 0;
        GoodsVo existGood = getExistGood(request.getGoodNo(), request.getSupplierNo());

        if (ObjectUtil.nonEmpty(existGood)) {
            GoodsVo goodsVo = new GoodsVo();
            goodsVo.setGoodno(request.getGoodNo());
            goodsVo.setPrice(request.getPrice());
            goodsVo.setOriginprice(request.getPrice());
            goodsVo.setNotaxprice(request.getNoTaxPrice());
            goodsVo.setUpdatedate(request.getUpdateDate());
            goodsVo.setUpdateuser(request.getUpdateUser());
            goodsVo.setUpdatetime(request.getUpdateTime());

            tbmqq436Mapper.addGoodLog(existGood.getGoodid());
            updateCount = tbmqq430Mapper.updateGoodInfo(goodsVo);
        }

        return updateCount;
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateZkhGoodQty (UpdateZkhGoodQtyRequest request) {

        GoodsVo goodsVo = new GoodsVo();
        goodsVo.setGoodid(request.getGoodId());
        goodsVo.setSupplierno(request.getSupplierNo());
        goodsVo.setQty(request.getQty());
        goodsVo.setUpdatedate(request.getUpdateDate());
        goodsVo.setUpdateuser(request.getUpdateUser());
        goodsVo.setUpdatetime(request.getUpdateTime());

        tbmqq436Mapper.addGoodLog(request.getGoodId());
        int updateCount = tbmqq430Mapper.updateGoodInfo(goodsVo);

        return updateCount;
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateZkhGoodDetail (UpdateZkhGoodDetailRequest request) {

        int updateCount = 0;
        GoodsVo existGood = getExistGood(request.getTbmqq430().getGoodno(), request.getTbmqq430().getSupplierno());

        if (ObjectUtil.nonEmpty(existGood)) {
            request.getTbmqq430().setGoodid(existGood.getGoodid());
            request.getTbmqq432().setGoodid(existGood.getGoodid());
            tbmqq436Mapper.addGoodLog(existGood.getGoodid());

            updateCount = tbmqq430Mapper.updateByPrimaryKeySelective(request.getTbmqq430());

            Tbmqq432 existTbmqq432 = tbmqq432Mapper.selectByPrimaryKey(existGood.getGoodid());
            if (ObjectUtil.nonEmpty(existTbmqq432)) {
                tbmqq432Mapper.updateByPrimaryKeySelective(request.getTbmqq432());
            }else{
                request.getTbmqq432().setGoodid(existGood.getGoodid());
                tbmqq432Mapper.insertSelective(request.getTbmqq432());
            }
        }

        return updateCount;

    }

    @Transactional(rollbackFor = Exception.class)
    public int updateZkhPic (UpdateZkhPicRequest request) {

        Map<String, Object> delMap = new HashMap();
        delMap.put("supplierNo", request.getTbmqq430().getSupplierno());
        delMap.put("goodId", request.getTbmqq430().getGoodid());
        tbmqq435Mapper.delGoodPic(delMap);

        tbmqq435Mapper.batchInsert(request.getTbmqq435List());

        tbmqq436Mapper.addGoodLog(request.getTbmqq430().getGoodid());

        GoodsVo goodsVo = new GoodsVo();
        goodsVo.setGoodid(request.getTbmqq430().getGoodid());
        goodsVo.setSupplierno(request.getTbmqq430().getSupplierno());
        goodsVo.setPictureurl(request.getTbmqq430().getPictureurl());
        goodsVo.setUpdatedate(request.getTbmqq430().getUpdatedate());
        goodsVo.setUpdateuser(request.getTbmqq430().getUpdateuser());
        goodsVo.setUpdatetime(request.getTbmqq430().getUpdatetime());
        int updateCount = tbmqq430Mapper.updateGoodInfo(goodsVo);

        return updateCount;
    }


    public int updateZkhPriceAndDetail (UpdateZkhPriceAndDetailRequest request) {

        tbmqq436Mapper.addGoodLog(request.getTbmqq430().getGoodid());

        GoodsVo goodsVo = new GoodsVo();
        goodsVo.setGoodid(request.getTbmqq430().getGoodid());
        goodsVo.setSupplierno(request.getTbmqq430().getSupplierno());
        goodsVo.setPrice(request.getTbmqq430().getPrice());
        goodsVo.setBrand(request.getTbmqq430().getBrand());
        goodsVo.setNotaxprice(request.getTbmqq430().getNotaxprice());
        goodsVo.setOriginprice(request.getTbmqq430().getOriginprice());
        goodsVo.setUpdatedate(request.getTbmqq430().getUpdatedate());
        goodsVo.setUpdateuser(request.getTbmqq430().getUpdateuser());
        goodsVo.setUpdatetime(request.getTbmqq430().getUpdatetime());
        int goodUpdateCount = tbmqq430Mapper.updateGoodInfo(goodsVo);


        Tbmqq432 existTbmqq432 = tbmqq432Mapper.selectByPrimaryKey(request.getTbmqq430().getGoodid());
        if (ObjectUtil.nonEmpty(existTbmqq432)) {

            tbmqq432Mapper.updateByPrimaryKeySelective(request.getTbmqq432());

        }else{

            request.getTbmqq432().setCreateuser(request.getTbmqq430().getUpdateuser());
            request.getTbmqq432().setCreatetime(request.getTbmqq430().getUpdatetime());
            request.getTbmqq432().setCreatedate(request.getTbmqq430().getUpdatedate());
            tbmqq432Mapper.insertSelective(request.getTbmqq432());

        }

        return goodUpdateCount;
    }


    private GoodsVo getExistGood (String goodNo, String supplierNo) {

        Map<String, Object> mapParam = new HashMap();
        mapParam.put("goodno", goodNo);
        mapParam.put("supplierno", supplierNo);

        GoodsVo existGood = tbmqq430Mapper.getGoodIdByGoodNo(mapParam);
        return existGood;
    }


}
