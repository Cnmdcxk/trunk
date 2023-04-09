package netplus.mall.service.biz;

import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.enums.YesNoEnum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.mall.api.enums.GoodsStatusEnum;
import netplus.mall.api.enums.PictureTypeEnum;
import netplus.mall.api.pojo.ygmalluser.Tbmqq429;
import netplus.mall.api.pojo.ygmalluser.Tbmqq432;
import netplus.mall.api.pojo.ygmalluser.Tbmqq435;
import netplus.mall.api.request.good.GetSupplierGoodEditInfoRequest;
import netplus.mall.api.request.good.SaveGoodInfoRequest;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.api.vo.Tbmqq435Vo;
import netplus.mall.api.vo.picLib.Tbmqq429Vo;
import netplus.mall.service.dao.*;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.rest.ServiceConfigRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.utils.date.NowDate;
import netplus.utils.number.NumberUtil;
import netplus.utils.object.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsEditBiz {

    @Autowired
    Tbmqq435Mapper tbmqq435Mapper;

    @Autowired
    Tbmqq430Mapper tbmqq430Mapper;

    @Autowired
    Tbmqq432Mapper tbmqq432Mapper;

    @Autowired
    Tbmqq429Mapper tbmqq429Mapper;

    @Autowired
    Tbmqq436Mapper tbmqq436Mapper;

    @Autowired
    IdentityRest identityRest;

    @Autowired
    ServiceConfigRest serviceConfigRest;

    public GoodsVo getSupplierGoodEditInfo (GetSupplierGoodEditInfoRequest request) {

        if (StringUtils.isEmpty(request)) {
            throw new NetPlusException("商品id不能为空");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("userNo", loginUserBean.getUserCode());
        mapParam.put("supplierNo", loginUserBean.getCompanyCode());

        GoodsVo goodsVo = tbmqq430Mapper.getSupplierGoodById(mapParam);
        if (ObjectUtil.isEmpty(goodsVo)) {
            throw new NetPlusException("商品不存在");
        }

        List<Tbmqq435Vo> goodPicList = tbmqq435Mapper.getSupplierGoodPicList(mapParam);
        goodsVo.setGoodPicList(goodPicList);

        return goodsVo;
    }


    @Transactional(rollbackFor = Exception.class)
    public void save(SaveGoodInfoRequest request){

        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        if (StringUtils.isEmpty(request.getGoodId())) {
            throw new NetPlusException("商品id不能为空");
        }

        if (ObjectUtil.isEmpty(request.getPrice())) {
            throw new NetPlusException("商品价格不能为空");
        }

        if (ObjectUtil.nonEmpty(request.getMainPicList()) && request.getMainPicList().size() > 10) {
            throw new NetPlusException("商品图片最多上传10张");
        }

        if (request.getIsIntTimePurchase().equals("Y")) {

            if (ObjectUtil.isEmpty(request.getMinBuyQty()) || NumberUtil.lte(request.getMinBuyQty(), NumberUtil.ZORE)) {
                throw new NetPlusException("选择按整数倍购买时，最小起订量不能为空或者小于等于0");
            }

        }

        NowDate now = new NowDate();
        String nowDate = now.getDateStr();
        String nowTime = now.getTimeStr();

        List<Tbmqq435> tbmqq435List = request.getMainPicList();
        List<Tbmqq429> insertTbmqq429List = new ArrayList();

        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("supplierNo", loginUserBean.getCompanyCode());
        GoodsVo goodsVo = tbmqq430Mapper.getSupplierGoodById(mapParam);

        if (ObjectUtil.isEmpty(goodsVo)) {
            throw new NetPlusException("商品不能存在");
        }


        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_OUT_SUPPLIER.code);
        if (loginUserBean.getCompanyName().equals(tbmqq461.getVal())) {

            if (!NumberUtil.equals(goodsVo.getPrice(), request.getPrice())) {
                throw new NetPlusException("第三方供应商不能修改价格");
            }

        }else{

            if ( NumberUtil.lt(request.getPrice(), NumberUtil.ZORE)) {
                throw new NetPlusException("商品价格必须大于0");
            }
        }

        if (!(goodsVo.getStatus().equals(GoodsStatusEnum.status_10.getCode())
                || goodsVo.getStatus().equals(GoodsStatusEnum.status_15.getCode())
                || goodsVo.getStatus().equals(GoodsStatusEnum.status_30.getCode())
        )) {
            throw new NetPlusException("当前商品状态不可修改");
        }


        if (NumberUtil.gt(request.getPrice(), goodsVo.getOriginprice())) {
            throw new NetPlusException("修改后价格不能大于长协价");
        }


        //添加商品修改前日志信息
        tbmqq436Mapper.addGoodLog(request.getGoodId());

        //商品主信息更新
        GoodsVo good = new GoodsVo();
        good.setGoodid(request.getGoodId());
        good.setPrice(request.getPrice());
        good.setNotaxprice(NumberUtil.noTaxPrice(request.getPrice(), goodsVo.getTax()));
        good.setPackthick(Long.valueOf(request.getPackThick()));
        good.setPackwidth(Long.valueOf(request.getPackWidth()));
        good.setPackheight(Long.valueOf(request.getPackHeight()));
        good.setPackunit(request.getPackUnit());
        good.setMinbuyqty(request.getMinBuyQty());
        good.setIsinttimepurchase(request.getIsIntTimePurchase());
        good.setUpdatedate(nowDate);
        good.setUpdatetime(nowTime);
        good.setUpdateuser(loginUserBean.getUserCode());

        if (ObjectUtil.nonEmpty(tbmqq435List) && tbmqq435List.size() > 0) {
            good.setPictureurl(tbmqq435List.get(0).getPictureurl());
        }else{
            good.setPictureurl("");
        }

        good.setOldStatusList(Arrays.asList(
                GoodsStatusEnum.status_10.getCode(),
                GoodsStatusEnum.status_15.getCode(),
                GoodsStatusEnum.status_30.getCode()
        ));

        int update430Count = tbmqq430Mapper.updateGoodInfo(good);
        if (update430Count != 1){
            throw new NetPlusException("商品主信息更新失败");
        }

        //商品介绍详情更新或新增
        Tbmqq432 tbmqq432 = new Tbmqq432();
        tbmqq432.setGoodid(request.getGoodId());
        tbmqq432.setContent(request.getContent());
        tbmqq432.setUpdatedate(nowDate);
        tbmqq432.setUpdatetime(nowTime);
        tbmqq432.setUpdateuser(loginUserBean.getUserCode());

        Tbmqq432 goodDetail = tbmqq432Mapper.selectByPrimaryKey(request.getGoodId());
        if (ObjectUtil.isEmpty(goodDetail)) {
            tbmqq432.setCreateuser(loginUserBean.getUserCode());
            tbmqq432.setCreatedate(nowDate);
            tbmqq432.setCreatetime(nowTime);

            tbmqq432Mapper.insert(tbmqq432);
        }else{
            int update432Count = tbmqq432Mapper.updateByPrimaryKeySelective(tbmqq432);
            if (update432Count != 1) {
                throw new NetPlusException("商品详情更新失败");
            }
        }

        //商品图片先删除后新增
        Map<String, Object> delGoodPicMap = new HashMap();
        delGoodPicMap.put("goodId",request.getGoodId());
        delGoodPicMap.put("supplierNo", loginUserBean.getCompanyCode());
        tbmqq435Mapper.delGoodPic(delGoodPicMap);


        int index = 1;
        for (Tbmqq435 t: tbmqq435List) {

            t.setGoodid(request.getGoodId());
            t.setSupplierno(loginUserBean.getCompanyCode());
            t.setPicturenum(String.valueOf(index));
            t.setUpdatetime(nowTime);
            t.setUpdatedate(nowDate);
            t.setUpdateuser(loginUserBean.getUserCode());
            t.setCreatetime(nowTime);
            t.setCreatedate(nowDate);
            t.setCreateuser(loginUserBean.getUserCode());
            t.setPicturenum(String.valueOf(index));
            index ++;
        }

        if (tbmqq435List.size() > 0) {
            tbmqq435Mapper.batchInsert(tbmqq435List);
        }

        //商品图库管理
        mapParam.put("matrlTm", goodsVo.getMatrltm());
        List<Tbmqq429Vo> tbmqq429VoList = tbmqq429Mapper.getGoodPicList(mapParam);
        List<String> tbmqq429keyList = tbmqq429VoList
                .stream()
                .filter( t -> t.getDeleted().equals(YesNoEnum.No.getValue()))
                .map(t -> String.format("%s||%s", t.getMatrltm(), t.getPicturenum()))
                .collect(Collectors.toList());

        //添加商品图库主图
        for (Tbmqq435 t: tbmqq435List) {

            String key = String.format("%s||%s", goodsVo.getMatrltm(), t.getPicturenum());
            if (!tbmqq429keyList.contains(key)) {

                Tbmqq429 tbmqq429 = new Tbmqq429();

                tbmqq429.setMatrlno(goodsVo.getMatrlno());
                tbmqq429.setMatrlid(goodsVo.getMatrlid());
                tbmqq429.setMatrltm(goodsVo.getMatrltm());
                tbmqq429.setPicturename(String.format("%s_%s", goodsVo.getMatrltm(), t.getPicturenum()));
                tbmqq429.setPicturenum(t.getPicturenum());
                tbmqq429.setSupplierno(loginUserBean.getCompanyCode());
                tbmqq429.setPicturetype(PictureTypeEnum.MAIN.getCode());
                tbmqq429.setGoodno(goodsVo.getGoodno());
                tbmqq429.setPictureurl(t.getPictureurl());
                tbmqq429.setCreateuser(loginUserBean.getUserCode());
                tbmqq429.setCreatedate(nowDate);
                tbmqq429.setCreatetime(nowTime);
                tbmqq429.setUpdateuser(loginUserBean.getUserCode());
                tbmqq429.setUpdatedate(nowDate);
                tbmqq429.setUpdatetime(nowTime);
                tbmqq429.setDeleted("N");
                insertTbmqq429List.add(tbmqq429);
            }
        }

        if (insertTbmqq429List.size() > 0) {
            tbmqq429Mapper.batchInsert(insertTbmqq429List);
        }

    }



}
