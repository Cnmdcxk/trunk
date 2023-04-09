package netplus.mall.service.biz;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import netplus.component.entity.data.KeyValue;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.enums.MsgTypeEnum;
import netplus.component.entity.enums.OneOrZeroEnum;
import netplus.component.entity.enums.YesNoEnum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.component.entity.request.RequestBase;
import netplus.excel.api.request.ExcelRequest;
import netplus.excel.api.request.GenExcelRequest;
import netplus.excel.api.rest.ExcelParse;
import netplus.excel.api.rest.ExcelRest;
import netplus.excel.api.rest.ExportRest;
import netplus.excel.api.rest.GetExcelDataRequest;
import netplus.excel.api.vo.ExcelDataVo;
import netplus.excel.api.vo.UploadResultVo;
import netplus.mall.api.enums.GoodsStatusEnum;
import netplus.mall.api.pojo.ygmalluser.Tbmqq427;
import netplus.mall.api.pojo.ygmalluser.Tbmqq428;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.api.pojo.ygmalluser.Tbmqq435;
import netplus.mall.api.request.GetGoodsDetailRequest;
import netplus.mall.api.request.GetGoodsListedRequest;
import netplus.mall.api.request.good.*;
import netplus.mall.api.vo.*;
import netplus.mall.api.vo.good.ImportGoodInfoVo;
import netplus.mall.api.vo.picLib.Tbmqq429Vo;
import netplus.mall.service.dao.*;
import netplus.mall.service.utils.ExcelUtil;
import netplus.messaging.api.request.SendMsgRequest;
import netplus.messaging.api.rest.MessagingRest;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.rest.ServiceConfigRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.utils.date.DateUtil;
import netplus.utils.date.NowDate;
import netplus.utils.excel.validation.Engine;
import netplus.utils.number.NumberUtil;
import netplus.utils.object.ObjectUtil;
import netplus.utils.pager.Pager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsListedBiz {


    protected Log logger = LogFactory.getLog(GoodsListedBiz.class);


    @Autowired
    private Tbmqq430Mapper tbmqq430Mapper;

    @Autowired
    private IdentityRest identityRest;

    @Autowired
    private Tbmqq428Mapper tbmqq428Mapper;

    @Autowired
    private Tbmqq427Mapper tbmqq427Mapper;

    @Autowired
    private Tbmqq436Mapper tbmqq436Mapper;


    @Autowired
    private ServiceConfigRest serviceConfigRest;

    @Autowired
    private ExportRest exportRest;

    @Autowired
    private ExcelRest excelRest;

    @Autowired
    private Tbmqq429Mapper tbmqq429Mapper;

    @Autowired
    private Tbmqq435Mapper tbmqq435Mapper;

    @Autowired
    private Tbmqq438Mapper tbmqq438Mapper;

    @Autowired
    private MessagingRest messagingRest;

    @Autowired
    private IfaceBiz ifaceBiz;

    /**
     * 查询商品列表
     *
     * @param request
     * @return
     */

    public PageBean getGoodsListedList(GetGoodsListedRequest request) {
        PageBean pageBean = new PageBean();
        Map<String, Object> mapParam = getMapParam(request);

        List<GoodsVo> goodsList = tbmqq430Mapper.getMyGoodsList(mapParam);
        int count = tbmqq430Mapper.getMyGoodsCount(mapParam);

        List<KeyValue> categoryPkListKV = tbmqq430Mapper.getCategoryPkListKV(mapParam);
        List<KeyValue> brandListKV = tbmqq430Mapper.getBrandListKV(mapParam);
        List<KeyValue> statusKV = tbmqq430Mapper.getStatusListKV(mapParam);
        List<KeyValue> supplierListKV = tbmqq430Mapper.getSupplierListKV(mapParam);
        List<KeyValue> agentListKV = tbmqq430Mapper.getAgentListKV(mapParam);

        goodsList.forEach(g -> g.setStatusName(GoodsStatusEnum.getName(g.getStatus())));
        statusKV.forEach(s -> s.setValue(GoodsStatusEnum.getName(s.getKey())));

        pageBean.addResultMap("categoryPkList", categoryPkListKV);
        pageBean.addResultMap("brandList", brandListKV);
        pageBean.addResultMap("statusList", statusKV);
        pageBean.addResultMap("supplierList", supplierListKV);
        pageBean.addResultMap("agent", agentListKV);

        pageBean.setItems(goodsList);
        pageBean.setTotalCount(count);
        return pageBean;
    }

    public Map<String, Object> getTabCount (GetGoodsListedRequest request) {

        Map<String, Object> mapParam = getMapParam(request);
        Map<String, Object> count = tbmqq430Mapper.getTabCount(mapParam);
        return count;
    }

    public Map<String,Object> getShelfCount(GetGoodsListedRequest request){
        Map<String, Object> map = getMapParam(request);
        Map<String,Object> count=tbmqq430Mapper.getShelfCount(map);
        return count;
    }

    public List<GoodsVo> getSiteTop (GetGoodsListedRequest request) {
         return tbmqq428Mapper.getTop();

    }

    private <T extends RequestBase> Map<String, Object>  getMapParam (T request) {

        LoginUserBean loginUserBean  = identityRest.getCurrentUser();
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);

        if ("S".equals(request.getPage())) {

            mapParam.put("supplierNo", loginUserBean.getCompanyCode());

        }else if ("B".equals(request.getPage())) {

            mapParam.put("userNo", loginUserBean.getUserCode());

        }else{

            //
        }

        return mapParam;

    }


    /**
     * 查询首页商品
     *
     * @param
     * @return
     */
    public  List<IndexGoodsVo> getIndexGoodsList(GetGoodsListedRequest request) {

        //获取商城首页配置
        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode("MALL_TOP");
        Type type =  new TypeToken<List<IndexGoodsVo>>() {}.getType();

        List<IndexGoodsVo> goods = new Gson().fromJson(tbmqq461.getVal(), type);

        Map<String,Object> map=new HashMap<>();

        //获取商城首页大类
        List<String> categoryNameList= new ArrayList<>();
        goods.forEach(v->{
            categoryNameList.add(v.getCategoryname());
        });
        map.put("categoryNameList",categoryNameList);
        //获取榜单前20的数据
        List<GoodsVo> list=tbmqq427Mapper.getTop(map);
        //根据一级分类将榜单数据进行分类
        if(ObjectUtil.nonEmpty(list)){
            Map<String,List<GoodsVo>> map2 = list.stream().collect(Collectors.groupingBy(GoodsVo::getCategoryname));
              goods.forEach(v1->{
                     if(map2.containsKey(v1.getCategoryname())){
                        v1.setGoodsVoList(map2.get(v1.getCategoryname()));
                     }
              });
            }
        return goods;

    }

    /**
     * 查看商品详情

     * @return GoodsVo
     */
    public List<GoodsVo> getGoodsDetail(GetGoodsDetailRequest request) {

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = new HashMap();
        mapParam.put("goodsId", request.getGoodsId());
        mapParam.put("matrltm",request.getMatrltm());
        mapParam.put("userNo", loginUserBean.getUserCode());

        return tbmqq430Mapper.getGoodsDetail(mapParam);
    }

    public void batchUpdatePrice (BatchUpdatePriceRequest request) {


        if (ObjectUtil.isEmpty(request.getGoodIdList()) || request.getGoodIdList().size() <= 0) {
            throw new NetPlusException("商品id不能为空");
        }

        if (StringUtils.isEmpty(request.getAddOrSub())) {
            throw new NetPlusException("加减操作不能为空");
        }

        if (ObjectUtil.isEmpty(request.getUpdatePriceRange())
                || NumberUtil.lte(request.getUpdatePriceRange(), NumberUtil.ZORE)) {

            throw new NetPlusException("加减价幅度必须大于0");
        }


        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_OUT_SUPPLIER.code);

        if (loginUserBean.getCompanyCode().equals(tbmqq461.getVal())) {
            throw new NetPlusException("第三方平台供应商不可调价");
        }

        Map<String, Object> mapParam = new HashMap();
        mapParam.put("goodIdList", request.getGoodIdList());
        mapParam.put("supplierNo", loginUserBean.getCompanyCode());
        List<GoodsVo> updateGoodList = new ArrayList();
        NowDate nowDate = new NowDate();

        List<GoodsVo> goodsVoList = tbmqq430Mapper.getGoodByIds(mapParam);
        if (ObjectUtil.isEmpty(goodsVoList) || goodsVoList.size() != request.getGoodIdList().size()) {
            throw new NetPlusException("部分状态商品不存在");
        }

        for (GoodsVo g: goodsVoList) {

            if (!(g.getStatus().equals(GoodsStatusEnum.status_10.getCode())
                    || g.getStatus().equals(GoodsStatusEnum.status_15.getCode())
                    || g.getStatus().equals(GoodsStatusEnum.status_30.getCode()))) {
                throw new NetPlusException("只有草稿、未上架、上架驳回状态的商品可以调价");
            }
        }


        for (GoodsVo g: goodsVoList) {

            GoodsVo updateGood = new GoodsVo();

            BigDecimal updatePrice;
            BigDecimal noTaxPrice;
            if (request.getAddOrSub().equals("1")) {

                updatePrice = NumberUtil.add2Price(g.getPrice(), request.getUpdatePriceRange());

            } else{

                updatePrice = NumberUtil.sub2Price(g.getPrice(), request.getUpdatePriceRange());
            }

            if (NumberUtil.lte(updatePrice, NumberUtil.ZORE)) {

                throw new NetPlusException("修改后价格必须大于0");

            }

            if (NumberUtil.gt(updatePrice, g.getOriginprice())) {

                throw new NetPlusException("修改后价格不能大于长协价");

            }

            noTaxPrice = NumberUtil.noTaxPrice(updatePrice, g.getTax());

            updateGood.setGoodid(g.getGoodid());
            updateGood.setSupplierno(loginUserBean.getCompanyCode());

            updateGood.setPrice(updatePrice);
            updateGood.setNotaxprice(noTaxPrice);
            updateGood.setOldStatusList(Arrays.asList(
                    GoodsStatusEnum.status_10.getCode(),
                    GoodsStatusEnum.status_15.getCode(),
                    GoodsStatusEnum.status_30.getCode()
            ));

            updateGood.setUpdatedate(nowDate.getDateStr());
            updateGood.setUpdatetime(nowDate.getTimeStr());
            updateGood.setUpdateuser(loginUserBean.getUserCode());
            updateGoodList.add(updateGood);
        }

        tbmqq436Mapper.addGoodLogs(request.getGoodIdList());

        for (GoodsVo g: updateGoodList) {
            int updateCount = tbmqq430Mapper.updateGoodInfo(g);
            if (updateCount != 1) {
                throw new NetPlusException(String.format("物料条码%s的商品调价失败", g.getMatrltm()));
            }

        }
    }

    public String getImportGoodTemp (GetGoodsListedRequest request) throws Exception {
        Map<String, Object> mapParam = getMapParam(request);
        List<GoodsVo> goodsList = tbmqq430Mapper.getMyGoodsList(mapParam);
        String tempName = "tmp_good_export";
        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(goodsList);
        genExcelRequest.setTemplateName(tempName);
        genExcelRequest.setSheetName("导入商品信息模版");
        String url = exportRest.genExcel(genExcelRequest);
        return url;
    }

    @Transactional(rollbackFor = Exception.class)
    public void importGoodInfo(ImportGoodInfoRequest request) {

        if (StringUtils.isEmpty(request.getBatchCode())) {
            throw new NetPlusException("导入商品信息批次号不能为空");

        }

        if (StringUtils.isEmpty(request.getFileUrl())) {
            throw new NetPlusException("导入商品信息文件地址不能为空");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_OUT_SUPPLIER.code);

        if (loginUserBean.getCompanyCode().equals(tbmqq461.getVal())) {
            throw new NetPlusException("第三方平台供应商不可以导入商品");
        }

        ExcelRequest excelRequest = new ExcelRequest();
        excelRequest.setBatchCode(request.getBatchCode());
        excelRequest.setFilePath(request.getFileUrl());
        excelRequest.setStartRowNum(0);

        UploadResultVo uploadResultVo = excelRest.uploadExcel(excelRequest);
        if (uploadResultVo.getTotal().intValue() <= 0) {
            throw new NetPlusException("excel内容不能为空");
        }

        GetExcelDataRequest getExcelDataRequest = new GetExcelDataRequest();
        getExcelDataRequest.setBatchCode(request.getBatchCode());
        ExcelDataVo excelDataVo = excelRest.getExcelData(getExcelDataRequest);

        List<ImportGoodInfoVo> vos = ExcelParse.parseItems(excelDataVo, ImportGoodInfoVo.class);
        vos.forEach(Engine::validate);

        NowDate nowDate = new NowDate();
        Set<String> matrltmSet = vos
                .stream()
                .map( v -> v.getMatrltm().getValue())
                .collect(Collectors.toSet());

        List<GoodsVo> goodsVoList = getGoodsList(matrltmSet, loginUserBean.getCompanyCode());

        if (goodsVoList.size() != matrltmSet.size()) {
            throw new NetPlusException("只有草稿、未上架、上架驳回状态的商品可以导入");
        }

        Map<String, GoodsVo> goodsVoMap = new HashMap();
        goodsVoList.forEach( g -> goodsVoMap.put(g.getMatrltm(), g));

        List<GoodsVo> updateGoodList = new ArrayList();
        for (ImportGoodInfoVo v: vos) {

            GoodsVo gvo = goodsVoMap.get(v.getMatrltm().getValue());
            BigDecimal price = NumberUtil.s2bPrice(v.getPrice().getValue());
            BigDecimal noTaxPrice = NumberUtil.noTaxPrice(price, gvo.getTax());

            if (NumberUtil.gt(price, gvo.getOriginprice())) {
                throw new NetPlusException("修改后价格不可以大于长协价");
            }

            GoodsVo updateGood = new GoodsVo();
            updateGood.setGoodid(gvo.getGoodid());
            updateGood.setSupplierno(loginUserBean.getCompanyCode());
            updateGood.setOldStatusList(Arrays.asList(
                    GoodsStatusEnum.status_10.getCode(),
                    GoodsStatusEnum.status_15.getCode(),
                    GoodsStatusEnum.status_30.getCode()
            ));

            updateGood.setPrice(price);
            updateGood.setNotaxprice(noTaxPrice);

            if (!StringUtils.isEmpty(v.getMinbuyqty().getValue())) {
                updateGood.setMinbuyqty(NumberUtil.s2bWeight(v.getMinbuyqty().getValue()));
            }

            updateGood.setUpdateuser(loginUserBean.getUserCode());
            updateGood.setUpdatetime(nowDate.getTimeStr());
            updateGood.setUpdatedate(nowDate.getDateStr());

            updateGoodList.add(updateGood);
        }

        for (GoodsVo g: updateGoodList) {
            tbmqq436Mapper.addGoodLog(g.getGoodid());
            int updateCount = tbmqq430Mapper.updateGoodInfo(g);
            if (updateCount != 1) {
                throw new NetPlusException(String.format("物料条码%s的商品导入失败", g.getMatrltm()));
            }
        }
    }

    private List<GoodsVo> getGoodsList (Set<String> matrltmSet, String supplierNo) {

        Map<String, Object> mapParam = new HashMap();
        mapParam.put("supplierNo", supplierNo);
        mapParam.put("statusList", Arrays.asList(
                GoodsStatusEnum.status_10.getCode(),
                GoodsStatusEnum.status_15.getCode(),
                GoodsStatusEnum.status_30.getCode()
        ));


        List<String> matrltmList = new ArrayList();
        matrltmList.addAll(matrltmSet);
        Pager<String> pager = new Pager(matrltmList, 1000);

        List<GoodsVo> goodsVoList = new ArrayList();
        for (int i=1; i <=pager.getTotalPage(); i++) {

            mapParam.put("matrltmList", pager.getPageData(i));
            List<GoodsVo> goodsVoPageList = tbmqq430Mapper.getGoodsByMatrltmList(mapParam);
            if (goodsVoPageList.size() > 0) {
                goodsVoList.addAll(goodsVoPageList);
            }
        }

        return goodsVoList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchApproval (BatchApprovalRequest request) {

        if (ObjectUtil.isEmpty(request.getGoodIdList())) {
            throw new NetPlusException("商品id不能为空");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();


        Map<String, Object> mapParam = new HashMap();
        mapParam.put("goodIdList", request.getGoodIdList());
        mapParam.put("supplierNo", loginUserBean.getCompanyCode());
        List<GoodsVo> goodsVoList = tbmqq430Mapper.getGoodByIds(mapParam);

        for (GoodsVo goodsVo: goodsVoList) {

            if (ObjectUtil.isEmpty(goodsVo.getPrice()) || NumberUtil.lte(goodsVo.getPrice(), BigDecimal.ZERO)) {
                throw new NetPlusException("价格必须大于0");
            }

            if (!checkOverdue(goodsVo.getPopricestartdate(), goodsVo.getPopricedate())) {
                throw new NetPlusException("不在协议有效期内，无法上架");
            }
        }

        NowDate nowDate = new NowDate();

        GoodsVo updateGood = new GoodsVo();
        updateGood.setGoodIdList(request.getGoodIdList());
        updateGood.setSupplierno(loginUserBean.getCompanyCode());
        updateGood.setOldStatusList(Arrays.asList(
                GoodsStatusEnum.status_10.getCode(),
                GoodsStatusEnum.status_15.getCode()
//                GoodsStatusEnum.status_30.getCode()
        ));
        updateGood.setStatus(GoodsStatusEnum.status_25.getCode());
        updateGood.setUpdateuser(loginUserBean.getUserCode());
        updateGood.setUpdatedate(nowDate.getDateStr());
        updateGood.setUpdatetime(nowDate.getTimeStr());

        tbmqq436Mapper.addGoodLogs(request.getGoodIdList());

        Map<String, Set<String>> matrlStatusMap = getMatrlStatus(request.getGoodIdList());

        int updateCount = tbmqq430Mapper.updateGoodInfo(updateGood);
        if (updateCount != request.getGoodIdList().size()) {
            throw new NetPlusException("上架失败");
        }

        //同步上架状态给仓库系统
        List<GoodsVo> finalGoodsVoList = new ArrayList();
        for (GoodsVo g: goodsVoList) {

            Set<String> statusSet = matrlStatusMap.get(g.getMatrltm());
            if (!statusSet.contains(GoodsStatusEnum.status_25.getCode())
                    && !statusSet.contains(GoodsStatusEnum.status_35.getCode())
                    && !statusSet.contains(GoodsStatusEnum.status_40.getCode())) {
                finalGoodsVoList.add(g);
            }
        }

        if (finalGoodsVoList.size() > 0) {
            ifaceBiz.sendOnOrOffLineStatus(goodsVoList, "Y");
        }


//        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_ON_LINE_HANDLER.code);
//        List<SendMsgRequest> sendMsgRequestList = new ArrayList();
//        List<String> receiveUserNoList = new Gson().fromJson(tbmqq461.getVal(), ArrayList.class);
//
//        for (String receiveUserNo: receiveUserNoList) {
//            SendMsgRequest sendMsgRequest = new SendMsgRequest();
//            sendMsgRequest.setSendUserNo(loginUserBean.getUserCode());
//            sendMsgRequest.setReceiveUserNo(receiveUserNo);
//            sendMsgRequest.setMsgType(MsgTypeEnum.UP_SHELVES);
//            sendMsgRequest.setParams(new String[]{loginUserBean.getCompanyName(), String.valueOf(request.getGoodIdList().size())});
//            sendMsgRequestList.add(sendMsgRequest);
//        }
//
//        messagingRest.sendMsg(sendMsgRequestList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchMatchPic (BatchMatchPicRequest request) {

        if (ObjectUtil.isEmpty(request.getGoodIdList())) {
            throw new NetPlusException("商品id不能为空");
        }

        if (StringUtils.isEmpty(request.getCover())) {
            throw new NetPlusException("覆盖操作不能为空");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        Map<String, Object> mapParam = new HashMap();
        mapParam.put("goodIdList", request.getGoodIdList());
        mapParam.put("supplierNo", loginUserBean.getCompanyCode());
//        mapParam.put("oldStatusList", Arrays.asList(
//                GoodsStatusEnum.status_10.getCode(),
//                GoodsStatusEnum.status_15.getCode(),
//                GoodsStatusEnum.status_30.getCode(),
//                GoodsStatusEnum.status_40.getCode()
//        ));

        List<GoodsVo> goodsVoList = tbmqq430Mapper.getGoodByIds(mapParam);
        if (ObjectUtil.isEmpty(goodsVoList) || goodsVoList.size() != request.getGoodIdList().size()) {
            throw new NetPlusException("商品信息不存在");
        }

        List<String> matrlTmList = goodsVoList
                .stream()
                .map( g -> g.getMatrltm())
                .collect(Collectors.toList());

        //获取物料图库信息
        Map<String, Object> getGoodPicListMap = new HashMap();
        getGoodPicListMap.put("matrlTmList", matrlTmList);
        getGoodPicListMap.put("supplierNo", loginUserBean.getCompanyCode());
        List<Tbmqq429Vo> tbmqq429VoList = tbmqq429Mapper.getGoodPicList(getGoodPicListMap);
        Map<String, List<Tbmqq429Vo>> groupData = tbmqq429VoList
                .stream()
                .filter(t -> t.getDeleted().equals(YesNoEnum.No.getValue()))
                .collect(Collectors.groupingBy(t -> t.getMatrltm()));


        //获取商品图片信息
        Map<String, Object> getSupplierGoodPicListMap = new HashMap();
        getSupplierGoodPicListMap.put("goodIdList", request.getGoodIdList());
        getSupplierGoodPicListMap.put("supplierNo", loginUserBean.getCompanyCode());
        List<Tbmqq435Vo> tbmqq435VoList = tbmqq435Mapper.getSupplierGoodPicList(getSupplierGoodPicListMap);

        Map<String, List<Tbmqq435Vo>> groupData2 = tbmqq435VoList
                .stream()
                .collect(Collectors.groupingBy(t -> t.getMatrltm()));



        List<Tbmqq435> insertList = new ArrayList();
        List<Tbmqq430> updateList = new ArrayList();
        NowDate nowDate = new NowDate();

        //覆盖
        if (OneOrZeroEnum.One.getValue().equals(request.getCover())) {

            for (GoodsVo g: goodsVoList) {

                List<Tbmqq429Vo> mainPicLib = groupData.get(g.getMatrltm());
                if (ObjectUtil.nonEmpty(mainPicLib)) {

                    mainPicLib.forEach( pp -> {

                        Tbmqq435 t = new Tbmqq435();
                        t.setGoodid(g.getGoodid());
                        t.setSupplierno(loginUserBean.getCompanyCode());
                        t.setPictureurl(pp.getPictureurl());
                        t.setPicturename(pp.getPicturename());
                        t.setPicturenum(pp.getPicturenum());
                        t.setCreateuser(loginUserBean.getUserCode());
                        t.setCreatedate(nowDate.getDateStr());
                        t.setCreatetime(nowDate.getTimeStr());
                        t.setUpdatedate(nowDate.getDateStr());
                        t.setUpdatetime(nowDate.getTimeStr());
                        t.setUpdateuser(loginUserBean.getUserCode());
                        insertList.add(t);

                    });

                    //更新商品图片
                    Tbmqq430 update = new Tbmqq430();
                    update.setGoodid(g.getGoodid());
                    update.setPictureurl(mainPicLib.get(0).getPictureurl());
                    update.setUpdatedate(nowDate.getDateStr());
                    update.setUpdatetime(nowDate.getTimeStr());
                    update.setUpdateuser(loginUserBean.getUserCode());
                    updateList.add(update);

                }


            }

            //删除原来图片
            Map<String, Object> delGoodPicMap = new HashMap();
            delGoodPicMap.put("goodIdList",request.getGoodIdList());
            delGoodPicMap.put("supplierNo", loginUserBean.getCompanyCode());
            tbmqq435Mapper.delGoodPic(delGoodPicMap);

        //不覆盖
        }else{


            for (GoodsVo g: goodsVoList) {

                    //物料条码主图图片
                    List<Tbmqq429Vo> mainPicLib = groupData.get(g.getMatrltm());

                    //如果物料条码主图图片存在继续下一步操作
                    if (ObjectUtil.nonEmpty(mainPicLib)) {

                        //商品主图图片
                        List<Tbmqq435Vo> goodPicList = groupData2.get(g.getMatrltm());

                        //如果商品主图图片存在继续下一步操作
                        if (ObjectUtil.nonEmpty(goodPicList)) {

                            List<String> picNumList = goodPicList
                                    .stream()
                                    .map( p -> p.getPicturenum())
                                    .collect(Collectors.toList());


                            for (Tbmqq429Vo vo: mainPicLib) {

                                //如果商品主图序号在图库中存在，则不处理
                                if (!picNumList.contains(vo.getPicturenum())) {

                                    Tbmqq435 t = new Tbmqq435();

                                    t.setGoodid(g.getGoodid());
                                    t.setSupplierno(loginUserBean.getCompanyCode());
                                    t.setPictureurl(vo.getPictureurl());
                                    t.setPicturename(vo.getPicturename());
                                    t.setPicturenum(vo.getPicturenum());

                                    t.setCreateuser(loginUserBean.getUserCode());
                                    t.setCreatedate(nowDate.getDateStr());
                                    t.setCreatetime(nowDate.getTimeStr());
                                    t.setUpdatedate(nowDate.getDateStr());
                                    t.setUpdatetime(nowDate.getTimeStr());
                                    t.setUpdateuser(loginUserBean.getUserCode());

                                    insertList.add(t);

                                    if (vo.getPicturenum().equals("1")) {

                                        //更新商品图片
                                        Tbmqq430 update = new Tbmqq430();
                                        update.setGoodid(g.getGoodid());
                                        update.setPictureurl(vo.getPictureurl());
                                        updateList.add(update);
                                    }
                                }


                            }

                        //如果商品主图图片不存在继续下一步操作
                        }else{

                            //将物料主图作为商品主图
                            mainPicLib.forEach( pp -> {

                                Tbmqq435 t = new Tbmqq435();

                                t.setGoodid(g.getGoodid());
                                t.setSupplierno(loginUserBean.getCompanyCode());
                                t.setPictureurl(pp.getPictureurl());
                                t.setPicturename(pp.getPicturename());
                                t.setPicturenum(pp.getPicturenum());

                                t.setCreateuser(loginUserBean.getUserCode());
                                t.setCreatedate(nowDate.getDateStr());
                                t.setCreatetime(nowDate.getTimeStr());
                                t.setUpdatedate(nowDate.getDateStr());
                                t.setUpdatetime(nowDate.getTimeStr());
                                t.setUpdateuser(loginUserBean.getUserCode());

                                insertList.add(t);
                            });
                        }

                        //更新商品图片
                        Tbmqq430 update = new Tbmqq430();
                        update.setGoodid(g.getGoodid());
                        update.setPictureurl(mainPicLib.get(0).getPictureurl());
                        update.setUpdatedate(nowDate.getDateStr());
                        update.setUpdatetime(nowDate.getTimeStr());
                        update.setUpdateuser(loginUserBean.getUserCode());
                        updateList.add(update);

                    }


            }
        }

        if (insertList.size() > 0) {
            tbmqq435Mapper.batchInsert(insertList);
        }

        for (Tbmqq430 t: updateList) {
            int updateCount = tbmqq430Mapper.updateByPrimaryKeySelective(t);
            if (updateCount != 1) {
                throw new NetPlusException("商品主图更新失败");
            }
        }
    }


    public String exportGoodsList(GetGoodsListedRequest request) throws  Exception{
        Map<String, Object> mapParam = getMapParam(request);
        List<GoodsVo> vos =tbmqq430Mapper.getMyGoodsList(mapParam);

        vos.forEach(vo -> {

            if(ObjectUtil.nonEmpty(vo.getLeadtimenum())){
                vo.setLeadtimenumStr(String.format("%s工作日", vo.getLeadtimenum()));
            }

            String bandAndProductName=vo.getProductname();
            if(vo.getBrand()!=null && !StringUtils.isEmpty(vo.getBrand().trim())){
                bandAndProductName=bandAndProductName+"/"+vo.getBrand();
            }
            vo.setBandAndProductName(bandAndProductName);
            String t=String.valueOf(vo.getTax().multiply(BigDecimal.valueOf(100)));
            String tax= t.substring(0,t.indexOf(".")) +"%";
            vo.setTaxStr(tax);
            vo.setCreateTimeStr(DateUtil.format(vo.getCreatedate()+vo.getCreatetime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
            vo.setUpdateTimeStr(DateUtil.format(vo.getUpdatedate()+vo.getUpdatetime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
            vo.setPopricedateStr(DateUtil.format(vo.getPopricedate()+vo.getPopricetime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
            vo.setPopricestartdateStr(DateUtil.format(vo.getPopricestartdate()+vo.getPopricestarttime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
            vo.setStatusName(GoodsStatusEnum.getName(vo.getStatus()));

        });

        String tempName = "";
        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(vos);

        if(request.getPage().equals("A")){
            tempName="tmp_pur_goodsAudit_info";
        }else if(request.getPage().equals("S")){
            tempName="tmp_sale_goodsList_info";
        }

        genExcelRequest.setTemplateName(tempName);
        genExcelRequest.setSheetName("导出挂牌商品明细");
        String url = exportRest.genExcel(genExcelRequest);
        return url;
    }

    public void exportNewGoodsList(HttpServletResponse response, String json) throws  Exception{

        GetGoodsListedRequest request = new Gson().fromJson(json,GetGoodsListedRequest.class);

            ExcelUtil.build()
                    .setData( page -> {
                        if (page > 100) {
                            return null;
                        }
                        request.setPageSize(200);
                        request.setPageIndex(page);
                        Map<String, Object> mapParam = getMapParam(request);
                        List<GoodsVo> vos =tbmqq430Mapper.getMyGoodsList(mapParam);
                        vos.forEach(vo -> {

                            if(ObjectUtil.nonEmpty(vo.getLeadtimenum())){
                                vo.setLeadtimenumStr(String.format("%s工作日", vo.getLeadtimenum()));
                            }

                            String bandAndProductName=vo.getProductname();
                            if(vo.getBrand()!=null && !StringUtils.isEmpty(vo.getBrand().trim())){
                                bandAndProductName=bandAndProductName+"/"+vo.getBrand();
                            }
                            vo.setBandAndProductName(bandAndProductName);
                            String t=String.valueOf(vo.getTax().multiply(BigDecimal.valueOf(100)));
                            String tax= t.substring(0,t.indexOf(".")) +"%";
                            vo.setTaxStr(tax);
                            vo.setCreateTimeStr(DateUtil.format(vo.getCreatedate()+vo.getCreatetime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
                            vo.setUpdateTimeStr(DateUtil.format(vo.getUpdatedate()+vo.getUpdatetime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
                            vo.setPopricedateStr(DateUtil.format(vo.getPopricedate()+vo.getPopricetime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
                            vo.setPopricestartdateStr(DateUtil.format(vo.getPopricestartdate()+vo.getPopricestarttime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
                            vo.setStatusName(GoodsStatusEnum.getName(vo.getStatus()));

                        });
                        return vos;
                    })
                    .setHead(GoodsAuditExcelOutVo.class)
                    .setResponse(response)
                    .setName("商品挂牌查询导出")
                    .write();


    }

    public  String exportGoodsListChecked(GetGoodsListedRequest request) throws IOException {
        Map<String, Object> mapParam = getMapParam(request);
        List<GoodsVo> vos =tbmqq430Mapper.getMyGoodsList(mapParam);
        vos.forEach(g -> g.setStatusName(GoodsStatusEnum.getName(g.getStatus())));
        for (GoodsVo vo : vos) {
            if(vo.getReferdeliverydate()!=null) {
                String referdeliverydateStr = vo.getReferdeliverydate().toString() + "个工作日";
                vo.setReferdeliverydateStr(referdeliverydateStr);
            }
            else if(vo.getReferdeliverydate() == null){
                vo.setReferdeliverydateStr(" ");
            }
            String PonoPoitemno = vo.getPono()+"-"+vo.getPoitemno();
            if(PonoPoitemno !=null){
                vo.setPonopoitemno(PonoPoitemno);
            }
            BigDecimal tax = vo.getTax();
            if(tax !=null) {
                String s1 = tax.toString();
                vo.setTaxStr(s1 + "%");
            }

        }
        String tempName = "";
        if("A".equals(request.getPage())){
            tempName="tmp_checked_goodsAudit_info";
        }else{
            tempName="tmp_check_goodsAudit_info";
        }

        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(vos);
        genExcelRequest.setTemplateName(tempName);
        genExcelRequest.setSheetName("导出挂牌商品明细");
        String url = exportRest.genExcel(genExcelRequest);
        return url;
    }

    public String exportGroupGoodsUpdate(GetGoodsListedRequest request) throws IOException {
        Map<String, Object> mapParam =getMapParam(request);
        List<GoodsVo> vos =tbmqq430Mapper.getMyGoodsList(mapParam);
        vos.forEach(g -> g.setStatusName(GoodsStatusEnum.getName(g.getStatus())));
        for (GoodsVo vo : vos) {
            if(vo.getReferdeliverydate()!=null) {
                String referdeliverydateStr = vo.getReferdeliverydate().toString() + "个工作日";
                vo.setReferdeliverydateStr(referdeliverydateStr);
            }
            else if(vo.getReferdeliverydate() == null){
                vo.setReferdeliverydateStr(" ");
            }
        }
        String tempName = "";
        if("A".equals(request.getPage())){
            tempName="tmp_update_check_info";
        }else{
            tempName="tmp_update_checked_info";
        }

        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(vos);
        genExcelRequest.setTemplateName(tempName);
        genExcelRequest.setSheetName("导出商品修改审核明细");
        String url = exportRest.genExcel(genExcelRequest);
        return url;
    }

    public void setSitTop428(){
        NowDate nowDate=new NowDate();
        List<GoodsVo> tbmqq438VoList=tbmqq438Mapper.getRankingData();
        tbmqq438VoList.forEach(v->{
            Tbmqq428 tbmqq428=new Tbmqq428();
            tbmqq428.setSupplierno(v.getSupplierno());
            tbmqq428.setSuppliername(v.getSuppliername());
            tbmqq428.setQty(v.getQty());
            tbmqq428.setCreatedate(nowDate.getDateStr());
            tbmqq428.setMatrlno(v.getMatrlno());
            tbmqq428.setCreatetime(nowDate.getTimeStr());
            tbmqq428.setMatrltm(v.getMatrltm());
            tbmqq428.setMatrlid(v.getMatrlid());
            tbmqq428Mapper.insert(tbmqq428);
        });
    }

    public void setSitTop427(){
        NowDate nowDate=new NowDate();
        List<GoodsVo> tbmqq438VoList=tbmqq438Mapper.getShoppingList();
        tbmqq438VoList.forEach(v->{
            Tbmqq427 tbmqq427=new Tbmqq427();
            tbmqq427.setSupplierno(v.getSupplierno());
            tbmqq427.setSuppliername(v.getSuppliername());
            tbmqq427.setQty(v.getQty());
            tbmqq427.setCreatedate(nowDate.getDateStr());
            tbmqq427.setMatrlno(v.getMatrlno());
            tbmqq427.setCreatetime(nowDate.getTimeStr());
            tbmqq427.setMatrltm(v.getMatrltm());
            tbmqq427.setMatrlid(v.getMatrlid());
            tbmqq427Mapper.insert(tbmqq427);
        });
    }

    public int updateGoodInvalid(){
        Map<String, Object> map = new HashMap();
        NowDate nowDate = new NowDate();
        map.put("updateUser", SysCodeEnum.JOB.code);
        map.put("updateDate", nowDate.getDateStr());
        map.put("updateTime", nowDate.getTimeStr());
        map.put("currDate", nowDate.getDateStr());
        return tbmqq430Mapper.updateGoodInvalid(map);
    }

    public int updateGoodRank(){
        tbmqq428Mapper.clearData();
        setSitTop428();
        tbmqq427Mapper.clearData();
        setSitTop427();
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateApply(BatchUpdateApplyRequest request){

        if (ObjectUtil.isEmpty(request.getGoodIdList())) {
            throw new NetPlusException("商品id不能为空");
        }

        if (StringUtils.isEmpty(request.getApplyReason())) {
            throw new NetPlusException("申请理由不能为空");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        NowDate nowDate = new NowDate();

        GoodsVo updateGood = new GoodsVo();
        updateGood.setGoodIdList(request.getGoodIdList());
        updateGood.setSupplierno(loginUserBean.getCompanyCode());
        updateGood.setOldStatusList(Arrays.asList(
                GoodsStatusEnum.status_25.getCode(),
                GoodsStatusEnum.status_40.getCode()
        ));
        updateGood.setStatus(GoodsStatusEnum.status_35.getCode());
        updateGood.setApplyreason(request.getApplyReason());

        updateGood.setUpdateuser(loginUserBean.getUserCode());
        updateGood.setUpdatedate(nowDate.getDateStr());
        updateGood.setUpdatetime(nowDate.getTimeStr());


        tbmqq436Mapper.addGoodLogs(request.getGoodIdList());
        int updateCount = tbmqq430Mapper.updateGoodInfo(updateGood);
        if (updateCount != request.getGoodIdList().size()) {
            throw new NetPlusException("下架申请失败");
        }

        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_ON_LINE_HANDLER.code);
        List<SendMsgRequest> sendMsgRequestList = new ArrayList();
        List<String> receiveUserNoList = new Gson().fromJson(tbmqq461.getVal(), ArrayList.class);

        for (String receiveUserNo: receiveUserNoList) {
            SendMsgRequest sendMsgRequest = new SendMsgRequest();
            sendMsgRequest.setSendUserNo(loginUserBean.getUserCode());
            sendMsgRequest.setReceiveUserNo(receiveUserNo);
            sendMsgRequest.setMsgType(MsgTypeEnum.DOWN_SHELVES);
            sendMsgRequest.setParams(new String[]{loginUserBean.getCompanyName(), String.valueOf(request.getGoodIdList().size())});
            sendMsgRequestList.add(sendMsgRequest);
        }

        messagingRest.sendMsg(sendMsgRequestList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchConfirm(BatchAuditRequest request){
        if (ObjectUtil.isEmpty(request.getGoodIdList())) {
            throw new NetPlusException("商品id不能为空");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        String status = "";
        NowDate nowDate = new NowDate();
        GoodsVo updateGood = new GoodsVo();
        if(request.getBiz().equals("A")){

           if(request.getRejectOrAgree().equals("Y")){
              status=GoodsStatusEnum.status_25.getCode();
               updateGood.setShelvesdate(nowDate.getDateStr());
               updateGood.setShelvestime(nowDate.getTimeStr());
           }else{
              status=GoodsStatusEnum.status_30.getCode();
           }

        }else{

            if(request.getRejectOrAgree().equals("Y")){
                status=GoodsStatusEnum.status_15.getCode();
            }else{
                status=GoodsStatusEnum.status_40.getCode();
            }
        }


        updateGood.setGoodIdList(request.getGoodIdList());
        updateGood.setStatus(status);
        updateGood.setUpdatetime(nowDate.getTimeStr());
        updateGood.setUpdatedate(nowDate.getDateStr());
        updateGood.setUpdateuser(loginUserBean.getUserCode());
        if (request.getRejectOrAgree().equals("N")) {

            if (ObjectUtil.isEmpty(request.getRejectReason())) {
                throw new NetPlusException("驳回原因不能为空");
            }
        }

        updateGood.setRejectreason(request.getRejectReason());
        updateGood.setAudituser(loginUserBean.getUserCode());
        tbmqq436Mapper.addGoodLogs(request.getGoodIdList());

        int updateCount = tbmqq430Mapper.updateGoodInfo(updateGood);
        if (updateCount != request.getGoodIdList().size()) {
            throw new NetPlusException("操作失败");
        }
        if(status.equals(GoodsStatusEnum.status_25.getCode())) {
            afterAuditCreateMsg(request.getGoodIdList(), loginUserBean, MsgTypeEnum.UP_SHELVES_ACCEPT);
        }else if(status.equals(GoodsStatusEnum.status_30.getCode())){
            afterAuditCreateMsg(request.getGoodIdList(), loginUserBean, MsgTypeEnum.UP_SHELVES_REJECT);
        }else if(status.equals(GoodsStatusEnum.status_15.getCode())){

            afterAuditCreateMsg(request.getGoodIdList(), loginUserBean, MsgTypeEnum.DOWN__SHELVES_ACCEPT);

            sendOffLineStatus(request.getGoodIdList());
        }else {
            afterAuditCreateMsg(request.getGoodIdList(), loginUserBean, MsgTypeEnum.DOWN_SHELVES_REJECT);
        }
    }

    public String exportConfirm(GetGoodsListedRequest request) throws Exception {

        Map<String, Object> mapParam =getMapParam(request);

        List<GoodsVo> vos =tbmqq430Mapper.getMyGoodsList(mapParam);

        vos.forEach(g -> g.setStatusName(GoodsStatusEnum.getName(g.getStatus())));

        for (GoodsVo vo : vos) {
            String reDate=vo.getLeadtimenum()==null? "":vo.getLeadtimenum()+"个工作日";
            vo.setLeadtimenumStr(reDate);
            String t=String.valueOf(vo.getTax().multiply(BigDecimal.valueOf(100)));
            String tax= t.substring(0,t.indexOf(".")) +"%";
            vo.setTaxStr(tax);
            vo.setPriceStr(String.format("%.2f",vo.getPrice()));
            vo.setOriginpriceStr(String.format("%.2f",vo.getOriginprice()));
            vo.setPopricedate(DateUtil.format(vo.getPopricedate()+vo.getPopricetime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
            vo.setPopricestartdate(DateUtil.format(vo.getPopricestartdate()+vo.getPopricestarttime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
            vo.setCreatedate(DateUtil.format(vo.getCreatedate()+vo.getCreatetime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
            vo.setUpdatedate(DateUtil.format(vo.getUpdatedate()+vo.getUpdatetime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
        }
        String tempName = "";
        if("A".equals(request.getPage())){
            tempName="tmp_up_confirm_info";
        }else{
            tempName="tmp_down_confirm_info";
        }

        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(vos);
        genExcelRequest.setTemplateName(tempName);
        genExcelRequest.setSheetName("导出商品上下架确认明细");
        String url = exportRest.genExcel(genExcelRequest);
        return url;


    }

    public void afterAuditCreateMsg (List<String> goodIdList, LoginUserBean loginUserBean, MsgTypeEnum msgTypeEnum) {

        Map<String, Object> mapParam = new HashMap();
        mapParam.put("goodIdList", goodIdList);
        List<GoodsVo> goodsVoList = tbmqq430Mapper.getGoodByIds(mapParam);


        Map<String, List<GoodsVo>> groupData = goodsVoList
                .stream()
                .collect(Collectors.groupingBy(g -> String.format("%s-%s", g.getSupplierno(), g.getSuppliername())));


        List<SendMsgRequest> sendMsgRequestList = new ArrayList();

        for (Map.Entry<String, List<GoodsVo>> entry: groupData.entrySet()) {
            String supplierNo = "M" + entry.getKey().split("-")[0];
//            String supplierName = entry.getKey().split("-")[1];
            String count = String.valueOf(entry.getValue().size());

            String[] param;
            String userid;

            if (MsgTypeEnum.UP_SHELVES_ACCEPT.getTypeCode()==msgTypeEnum.getTypeCode()) {
                userid=supplierNo;
                param = new String[]{count};

            } else if(MsgTypeEnum.UP_SHELVES_REJECT.getTypeCode()==msgTypeEnum.getTypeCode()){
                userid=supplierNo;
                param=new String[]{count,loginUserBean.getUserCode(),loginUserBean.getUsername()};
            }else if(MsgTypeEnum.DOWN__SHELVES_ACCEPT.getTypeCode()==msgTypeEnum.getTypeCode()){
                userid=supplierNo;
                param=new String[]{count};
            }else if(MsgTypeEnum.DOWN_SHELVES_REJECT.getTypeCode()==msgTypeEnum.getTypeCode()){
                userid=supplierNo;
                param=new String[]{count,loginUserBean.getUserCode(),loginUserBean.getUsername()};
            }else {
                throw new NetPlusException("消息id不存在");
            }

            SendMsgRequest request=new SendMsgRequest();
            request.setSendUserNo(loginUserBean.getUserCode());
            request.setReceiveUserNo(userid);
            request.setMsgType(msgTypeEnum);
            request.setParams(param);
            sendMsgRequestList.add(request);

        }

        messagingRest.sendMsg(sendMsgRequestList);
    }

    public List<GoodsVo> getGoodBySupplierNo (GetGoodBySupplierNoRequest request) {
        return tbmqq430Mapper.getGoodBySupplierNo(request.getSupplierNo());
    }

    public List<GoodsVo> getPriceCompare (GetPriceCompareRequest request) {

        Map<String, Object> param = ObjectUtil.transBeanToMap(request);
        param.put("statusList", Arrays.asList(
                GoodsStatusEnum.status_25.getCode(),
                GoodsStatusEnum.status_35.getCode(),
                GoodsStatusEnum.status_40.getCode()
        ));

        List<GoodsVo> goodsVoList = tbmqq430Mapper.getGoodsByMatrltmList(param);
        return goodsVoList;
    }

    public int delInvalidGood(){

        int delCount = 0;
        List<String> goodIdList = tbmqq430Mapper.getExpiredGoodIdList();

        if (goodIdList.size() > 0) {

            sendOffLineStatus(goodIdList);

            delCount = tbmqq430Mapper.delInvalidGood();
        }

        return delCount;
    }

    private boolean checkOverdue (String popriceStartDate, String popriceEndDate) {
        long nowLong = Long.valueOf(DateUtil.format(new Date(), "yyyyMMdd"));
        long popriceStartDateLong = Long.valueOf(popriceStartDate);
        long popriceEndDateLong = Long.valueOf(popriceEndDate);
        if (nowLong >= popriceStartDateLong && nowLong <= popriceEndDateLong) {
            return true;
        }else{
            return false;
        }
    }

    private Map<String, Set<String>> getMatrlStatus (List<String> goodIdList) {


        List<GoodsVo> totalGoodsVos = new ArrayList();

        Map<String, Object> map = new HashMap();
        Pager<String> goodIdListPager = new Pager(goodIdList, 1000);
        for (int i=1; i<=goodIdListPager.getTotalPage(); i++) {

            map.put("goodIdList", goodIdListPager.getPageData(i));
            List<GoodsVo> goodsVos = tbmqq430Mapper.getMatrlStatus(map);

            if (ObjectUtil.nonEmpty(goodsVos)) {
                totalGoodsVos.addAll(goodsVos);
            }
        }

        Map<String, Set<String>> groupData = totalGoodsVos
                .stream()
                .collect(Collectors.groupingBy(
                        GoodsVo::getMatrltm,
                        Collectors.mapping(g -> g.getStatus(), Collectors.toSet()))
                );

        return groupData;
    }

    //同步下架状态给仓库系统
    private void sendOffLineStatus (List<String> goodIdList) {
        Map<String, Set<String>> matrlStatusMap = getMatrlStatus(goodIdList);

        List<GoodsVo> totalGoodsVos = new ArrayList();

        Map<String, Object> mapParam = new HashMap();
        Pager<String> goodIdListPager = new Pager(goodIdList, 1000);
        for (int i=1; i<=goodIdListPager.getTotalPage(); i++) {

            mapParam.put("goodIdList", goodIdListPager.getPageData(i));
            List<GoodsVo> goodsVoList = tbmqq430Mapper.getGoodByIds(mapParam);

            if (ObjectUtil.nonEmpty(goodsVoList)) {
                totalGoodsVos.addAll(goodsVoList);
            }
        }

        logger.info(String.format("totalGoodsVos size: %d", totalGoodsVos.size()));


        List<GoodsVo> finalGoodsVoList = new ArrayList();
        for (GoodsVo g: totalGoodsVos) {

            Set<String> statusSet = matrlStatusMap.get(g.getMatrltm());
            if (!statusSet.contains(GoodsStatusEnum.status_25.getCode())
                    && !statusSet.contains(GoodsStatusEnum.status_35.getCode())
                    && !statusSet.contains(GoodsStatusEnum.status_40.getCode())) {
                finalGoodsVoList.add(g);
            }
        }

        logger.info(String.format("finalGoodsVoList size: %d", totalGoodsVos.size()));


        if (finalGoodsVoList.size() > 0) {
            ifaceBiz.sendOnOrOffLineStatus(totalGoodsVos, "N");
        }
    }



}
