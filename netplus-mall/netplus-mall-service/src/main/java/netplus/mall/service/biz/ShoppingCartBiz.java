package netplus.mall.service.biz;

import netplus.component.entity.data.KeyValue;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.enums.YesNoEnum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.response.ApiResponse;
import netplus.excel.api.request.ExcelRequest;
import netplus.excel.api.request.GenExcelRequest;
import netplus.excel.api.rest.ExcelParse;
import netplus.excel.api.rest.ExcelRest;
import netplus.excel.api.rest.ExportRest;
import netplus.excel.api.rest.GetExcelDataRequest;
import netplus.excel.api.vo.ExcelDataVo;
import netplus.excel.api.vo.UploadResultVo;
import netplus.joint.erp.api.response.out.JK0010.JK0010SubResponse;
import netplus.mall.api.enums.GoodsStatusEnum;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.api.pojo.ygmalluser.Tbmqq433;
import netplus.mall.api.request.good.GetPriceCompareRequest;
import netplus.mall.api.request.order.AddShoppingCartFromOrderRequest;
import netplus.mall.api.request.shoppingCart.*;
import netplus.mall.api.request.shoppingCart.erpAdd.ErpAddShoppingCartGoodsRequest;
import netplus.mall.api.request.shoppingCart.erpAdd.ErpAddShoppingCartRequest;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.api.vo.ShoppingCartVo;
import netplus.mall.api.vo.goodCollect.GoodCollectVo;
import netplus.mall.api.vo.order.Tbmqq441Vo;
import netplus.mall.api.vo.shoppingCart.ImportShoppingCartResultVo;
import netplus.mall.api.vo.shoppingCart.ImportShoppingCartVo;
import netplus.mall.api.vo.shoppingCart.Tbmqq433Vo;
import netplus.mall.service.dao.Tbmqq430Mapper;
import netplus.mall.service.dao.Tbmqq433Mapper;
import netplus.mall.service.dao.Tbmqq434Mapper;
import netplus.mall.service.dao.Tbmqq441Mapper;
import netplus.provider.api.enums.UserTypeEnums;
import netplus.provider.api.request.GetUserInfoRequest;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.utils.date.DateUtil;
import netplus.utils.date.NowDate;
import netplus.utils.excel.validation.Engine;
import netplus.utils.number.NumberUtil;
import netplus.utils.object.ObjectUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShoppingCartBiz {

    protected Log logger = LogFactory.getLog(ShoppingCartBiz.class);

    @Autowired
    private Tbmqq433Mapper tbmqq433Mapper;

    @Autowired
    private Tbmqq430Mapper tbmqq430Mapper;

    @Autowired
    private IdentityRest identityRest;

    @Autowired
    private ExcelRest excelRest;

    @Autowired
    private ExportRest exportRest;

    @Autowired
    private Tbmqq441Mapper tbmqq441Mapper;

    @Autowired
    private Tbmqq434Mapper tbmqq434Mapper;

    @Autowired
    GoodsListedBiz goodsListedBiz;

    @Autowired
    IfaceBiz ifaceBiz;

    @Autowired
    private BasicDataBiz basicDataBiz;

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse addShoppingCartFromOrder (AddShoppingCartFromOrderRequest request) {

        if (StringUtils.isEmpty(request.getOrderNo())) {
            throw new NetPlusException("订单号不能为空");
        }

        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        List<Tbmqq441Vo> tbmqq441VoList = tbmqq441Mapper.getOrderDetail(mapParam);

        if (ObjectUtil.isEmpty(tbmqq441VoList)) {
            throw new NetPlusException("订单明细不存在");
        }

        List<GoodsVo> goodsVoList = getGoods(tbmqq441VoList);
        if (ObjectUtil.isEmpty(goodsVoList)) {
            throw new NetPlusException("订单商品已下架或不存在");
        }




        List<String> onLineGoodIdList = goodsVoList
                .stream()
                .map(g -> g.getGoodid())
                .collect(Collectors.toList());

        Map<String, Tbmqq441Vo> tbmqq441VoMap = tbmqq441VoList
                .stream()
                .collect(Collectors.toMap(Tbmqq441Vo::getGoodid, t -> t));



        NowDate nowDate = new NowDate();
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> getMyShoppingCartListMap = ObjectUtil.transBeanToMap(request);
        getMyShoppingCartListMap.put("userno", loginUserBean.getUserCode());
        getMyShoppingCartListMap.put("goodIdList", onLineGoodIdList);
        List<Tbmqq433Vo> tbmqq433VoList = tbmqq433Mapper.getMyShoppingCartList(getMyShoppingCartListMap);

        List<String> cartGoodIdList = tbmqq433VoList
                .stream()
                .map( t -> t.getGoodid())
                .collect(Collectors.toList());

        List<Tbmqq433> insertList = new ArrayList();

        for (GoodsVo g: goodsVoList) {

            if (!cartGoodIdList.contains(g.getGoodid())) {

                Tbmqq441Vo tbmqq441Vo = tbmqq441VoMap.get(g.getGoodid());

                Tbmqq433 tbmqq433 = new Tbmqq433();
                BeanUtils.copyProperties(g, tbmqq433);

                tbmqq433.setQty(tbmqq441Vo.getQty());
                tbmqq433.setUserno(loginUserBean.getUserCode());
                tbmqq433.setUsername(loginUserBean.getUsername());
                tbmqq433.setCreatedate(nowDate.getDateStr());
                tbmqq433.setCreatetime(nowDate.getTimeStr());
                tbmqq433.setCreateuser(loginUserBean.getUserCode());
                tbmqq433.setUpdateuser(loginUserBean.getUserCode());
                tbmqq433.setUpdatetime(nowDate.getTimeStr());
                tbmqq433.setUpdatedate(nowDate.getDateStr());
                insertList.add(tbmqq433);
            }
        }


        if (insertList.size() > 0) {
            batchInsert(insertList,loginUserBean.getUserCode());
        }

        Map<String, Integer> result = new HashMap();
        result.put("invalidNum", tbmqq441VoList.size() - goodsVoList.size());
        result.put("addNum", insertList.size());
        result.put("existNum", goodsVoList.size() - insertList.size());

        //最终影响商品数量
        ApiResponse resp = ApiResponse.success(result);
        return  resp;
    }

    private List<GoodsVo> getGoods (List<Tbmqq441Vo> tbmqq441VoList) {

        List<String> goodIdList = tbmqq441VoList
                .stream()
                .map( t -> t.getGoodid())
                .collect(Collectors.toList());


        Map<String, Object> mapParam = new HashMap();
        mapParam.put("goodIdList", goodIdList);
        mapParam.put("statusList", Arrays.asList(
                GoodsStatusEnum.status_25.getCode(),
                GoodsStatusEnum.status_35.getCode(),
                GoodsStatusEnum.status_40.getCode()
        ));

        List<GoodsVo> goodsVoList = tbmqq430Mapper.getGoodByIds(mapParam);

        return goodsVoList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addShoppingCart (AddShoppingCartRequest request) {


        if (StringUtils.isEmpty(request.getGoodId())) {
            throw new NetPlusException("goodId不能为空");
        }

        NowDate nowDate = new NowDate();
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        GoodsVo goodsVo = tbmqq430Mapper.getGoodsById(request.getGoodId());

        if (ObjectUtil.isEmpty(goodsVo)) {
            throw new NetPlusException("商品不存在");
        }

        if (!(goodsVo.getStatus().equals(GoodsStatusEnum.status_25.getCode())
                ||goodsVo.getStatus().equals(GoodsStatusEnum.status_35.getCode())
                ||goodsVo.getStatus().equals(GoodsStatusEnum.status_40.getCode())
        )) {

            throw new NetPlusException("商品未上架");
        }

        if(!basicDataBiz.checkMatrlQualityAndDeptByMatrlId(loginUserBean,Arrays.asList(goodsVo.getMatrlid()))){
            throw new NetPlusException("常备件可直接领用，若无库存，请联系物资总库下单");
        }

        //默认数量加入购物车的, 如果有最小起订量，则默认是最小起订量, 不然就是默认1
        if ("Y".equals(request.getIsDefaultAdd())) {


            if (ObjectUtil.nonEmpty(goodsVo.getMinbuyqty())){
                request.setQty(goodsVo.getMinbuyqty());
            }else{
                request.setQty(NumberUtil.ONE);
            }

        }else{

            if (ObjectUtil.isEmpty(request.getQty())) {
                throw new NetPlusException("购买数量不能为空");
            }

            if (NumberUtil.lte(request.getQty(), NumberUtil.ZORE)) {
                throw new NetPlusException("购买数量必须大于0");
            }


            if (ObjectUtil.nonEmpty(goodsVo.getMinbuyqty())
                    && NumberUtil.gt(goodsVo.getMinbuyqty(), NumberUtil.ZORE)) {

                if (NumberUtil.lt(request.getQty(), goodsVo.getMinbuyqty())) {
                    throw new NetPlusException("购买数量必须大于最小起订量");
                }

                if (YesNoEnum.Yes.getValue().equals(goodsVo.getIsinttimepurchase())) {

                    if (!NumberUtil.equals(
                            NumberUtil.ZORE,
                            NumberUtil.surplus(request.getQty(), goodsVo.getMinbuyqty())
                    )) {
                        throw new NetPlusException("购买数量必须是最小起订量的整数倍");
                    }
                }
            }
        }

        if (!checkOverdue(goodsVo.getPopricestartdate(), goodsVo.getPopricedate())) {
            throw new NetPlusException("不在协议有效期内，无法加入购物车");
        }

        Map<String, Object> getMyShoppingCartByGoodIdMap = new HashMap();
        getMyShoppingCartByGoodIdMap.put("userNo", loginUserBean.getUserCode());
        getMyShoppingCartByGoodIdMap.put("goodId", request.getGoodId());
        Tbmqq433Vo existTbmqq433Vo = tbmqq433Mapper.getMyShoppingCartByGoodId(getMyShoppingCartByGoodIdMap);

        if (ObjectUtil.isEmpty(existTbmqq433Vo)) {


            Map<String, Object> mapParam = new HashMap();
            mapParam.put("userno", loginUserBean.getUserCode());
            mapParam.put("goodIdList", Arrays.asList(goodsVo.getGoodid()));
            List<GoodCollectVo> remarks=tbmqq434Mapper.getMyCollectListRemark(mapParam);


            Tbmqq433 tbmqq433 = new Tbmqq433();
            BeanUtils.copyProperties(goodsVo, tbmqq433);
            tbmqq433.setQty(request.getQty());
            tbmqq433.setUserno(loginUserBean.getUserCode());
            tbmqq433.setUsername(loginUserBean.getUsername());
            tbmqq433.setCreatedate(nowDate.getDateStr());
            tbmqq433.setCreatetime(nowDate.getTimeStr());
            tbmqq433.setCreateuser(loginUserBean.getUserCode());
            tbmqq433.setUpdatedate(nowDate.getDateStr());
            tbmqq433.setUpdatetime(nowDate.getTimeStr());
            tbmqq433.setUpdateuser(loginUserBean.getUserCode());
            if(!ObjectUtils.isEmpty(remarks)){
                tbmqq433.setRemark(remarks.get(0).getRemark());
            }
            tbmqq433Mapper.insertSelective(tbmqq433);

        }else{

            Tbmqq433 tbmqq433 = new Tbmqq433();
            tbmqq433.setGoodid(existTbmqq433Vo.getGoodid());
            tbmqq433.setUserno(loginUserBean.getUserCode());

            if (YesNoEnum.Yes.getValue().equals(request.getIsAddUp())) {
                tbmqq433.setQty( NumberUtil.add(existTbmqq433Vo.getQty(), request.getQty()) );
            }else{
                tbmqq433.setQty(request.getQty());
            }

            tbmqq433.setUpdatedate(nowDate.getDateStr());
            tbmqq433.setUpdatetime(nowDate.getTimeStr());
            tbmqq433.setUpdateuser(loginUserBean.getUserCode());

            int count = tbmqq433Mapper.updateByPrimaryKeySelective(tbmqq433);
            if (count != 1) {
                throw new NetPlusException("更新购物车失败");
            }

        }
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

    public PageBean<ShoppingCartVo> getMyShoppingCartList(GetMyShoppingCartRequest request) {

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("userno", loginUserBean.getUserCode());

        PageBean<ShoppingCartVo> pageBean = new PageBean();
        List<Tbmqq433Vo> tbmqq433VoList = tbmqq433Mapper.getMyShoppingCartList(mapParam);

        Set<String> matrlTmSet = tbmqq433VoList
                .stream()
                .map( t -> t.getMatrltm())
                .collect(Collectors.toSet());

        //获取最低价
        if (matrlTmSet.size() > 0) {

            GetPriceCompareRequest getPriceCompareRequest = new GetPriceCompareRequest();
            getPriceCompareRequest.setMatrltmList(new ArrayList(matrlTmSet));
            List<GoodsVo> goodsVoList = goodsListedBiz.getPriceCompare(getPriceCompareRequest);

            Map<String, GoodsVo> goodGroupData = goodsVoList
                    .stream()
                    .collect(Collectors.toMap(
                            g -> g.getMatrltm(),
                            Function.identity(),
                            BinaryOperator.minBy(Comparator.comparing(GoodsVo :: getPrice))
                    ));


            for (Tbmqq433Vo tbmqq433Vo: tbmqq433VoList) {

                if (goodGroupData.keySet().contains(tbmqq433Vo.getMatrltm())) {

                    GoodsVo lowGoodsVo = goodGroupData.get(tbmqq433Vo.getMatrltm());
                    tbmqq433Vo.setLowPrice(lowGoodsVo.getPrice());
                    tbmqq433Vo.setLowQtyUnit(lowGoodsVo.getQtyunit());

                }
            }
        }

        List<ShoppingCartVo> shoppingCartVoList = new ArrayList();
        Map<String, List<Tbmqq433Vo>> groupData = tbmqq433VoList
                .stream()
                .collect(Collectors.groupingBy( t -> t.getPonopk()));

        for (Map.Entry<String, List<Tbmqq433Vo>> entry: groupData.entrySet()) {

            String ponopk = entry.getKey();
            List<Tbmqq433Vo> val = entry.getValue();

            String supplierno = val.get(0).getSupplierno();
            String suppliername = val.get(0).getSuppliername();

            ShoppingCartVo shoppingCartVo = new ShoppingCartVo();
            shoppingCartVo.setPonopk(ponopk);
            shoppingCartVo.setSupplierno(supplierno);
            shoppingCartVo.setSuppliername(suppliername);
            shoppingCartVo.setTbmqq433VoList(val);

            shoppingCartVoList.add(shoppingCartVo);
        }

        List<ShoppingCartVo> sortedShoppingCartVoList = shoppingCartVoList.stream().map(t -> {
            List<Tbmqq433Vo> sortList = t.getTbmqq433VoList().stream()
                    .sorted((t1, t2) -> {
                        Date t1CreatDate = DateUtil.valueOf(t1.getCreatedate() + t1.getCreatetime(), "yyyyMMddHHmmss");
                        Date t2CreateDate = DateUtil.valueOf(t2.getCreatedate() + t2.getCreatetime(), "yyyyMMddHHmmss");
                        return t1CreatDate.compareTo(t2CreateDate);
                    }).collect(Collectors.toList());
            t.setTbmqq433VoList(sortList);
            return t;
        }).sorted((t1, t2) -> {
            Tbmqq433Vo t1Min = t1.getTbmqq433VoList().get(0);
            Tbmqq433Vo t2Min = t2.getTbmqq433VoList().get(0);
            Date t1CreatDate = DateUtil.valueOf(t1Min.getCreatedate() + t1Min.getCreatetime(), "yyyyMMddHHmmss");
            Date t2CreateDate = DateUtil.valueOf(t2Min.getCreatedate() + t2Min.getCreatetime(), "yyyyMMddHHmmss");
            return t1CreatDate.compareTo(t2CreateDate);
        }).collect(Collectors.toList());


        List<KeyValue> categoryList = tbmqq433Mapper.getCategoryNameFilter(mapParam);
        List<KeyValue> brandList = tbmqq433Mapper.getBrandFilter(mapParam);

        pageBean.setItems(sortedShoppingCartVoList);
        pageBean.addResultMap("categoryList", categoryList);
        pageBean.addResultMap("brandList", brandList);

        return pageBean;
    }

    @Transactional(rollbackFor = Exception.class)
    public void delMyShoppingCart (DelMyShoppingCartRequest request) {

        if (ObjectUtil.isEmpty(request.getGoodIdList()) || request.getGoodIdList().size() <= 0) {
            throw new NetPlusException("商品id不能为空");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("userno", loginUserBean.getUserCode());

        if (!StringUtils.isEmpty(request.getOperation()) && request.getOperation().equals("B")) {

            mapParam.put("statusList", Arrays.asList(
                    GoodsStatusEnum.status_10.getCode(),
                    GoodsStatusEnum.status_15.getCode(),
                    GoodsStatusEnum.status_20.getCode(),
                    GoodsStatusEnum.status_30.getCode(),
                    GoodsStatusEnum.status_45.getCode()
            ));
        }

        int delCount = tbmqq433Mapper.delMyShoppingCart(mapParam);
        if (delCount != request.getGoodIdList().size()) {
            throw new NetPlusException("删除失败");
        }
    }

    public ApiResponse<ImportShoppingCartResultVo> importShoppingCart(ImportShoppingCartRequest request) {

        ApiResponse apiResponse = ApiResponse.success();

        if (request.getIsContinue().equals("N")) {

            if (ObjectUtil.isEmpty(request.getBatchCode())) {
                throw new NetPlusException("导入批次好不能为空");

            }

            if (StringUtils.isEmpty(request.getFileUrl())) {
                throw new NetPlusException("导入文件地址不能为空");
            }

            ExcelRequest excelRequest = new ExcelRequest();
            excelRequest.setBatchCode(request.getBatchCode());
            excelRequest.setFilePath(request.getFileUrl());
            excelRequest.setStartRowNum(0);

            UploadResultVo uploadResultVo = excelRest.uploadExcel(excelRequest);
            if (uploadResultVo.getTotal().intValue() <= 0) {
                throw new NetPlusException("excel内容不能为空");
            }
        }

        GetExcelDataRequest getExcelDataRequest = new GetExcelDataRequest();
        getExcelDataRequest.setBatchCode(request.getBatchCode());
        ExcelDataVo excelDataVo = excelRest.getExcelData(getExcelDataRequest);

        List<ImportShoppingCartVo> vos = ExcelParse.parseItems(excelDataVo, ImportShoppingCartVo.class);
        vos.forEach(Engine::validate);
        int errCount = vos.stream().filter( v -> !v.getIsValid()).collect(Collectors.toList()).size();
        if (errCount > 0) {
            throw new NetPlusException("模板不正确，请检查模板");
        }

        Map<String, Long> matrlTmGroupCount = vos
                .stream()
                .collect(Collectors.groupingBy(v -> v.getMatrlTm().getValue(), Collectors.counting()));

        Set<String> matrlTmSet = vos
                .stream()
                .filter( v -> StringUtils.isNotBlank(v.getMatrlTm().getValue()))
                .map( t-> t.getMatrlTm().getValue())
                .collect(Collectors.toSet());

        //导入物料号对应上架商品
        Map<String, List<GoodsVo>> goodsMap = getGoodsMap(matrlTmSet);

        for (ImportShoppingCartVo v: vos) {

            String matrlTm = v.getMatrlTm().getValue();

            if (!StringUtils.isEmpty(matrlTm)) {
                int count = matrlTmGroupCount.get(matrlTm).intValue();
                if (count >= 2) {
                    v.setIsValid(false);
                    v.setMessage("物料编码重复");
                }
            }

            if (ObjectUtil.isEmpty(goodsMap.get(matrlTm))) {
                v.setIsValid(false);
                v.setMessage(String.format("商城暂无此物料条码的商品"));
            }

            if (ObjectUtil.nonEmpty(goodsMap.get(matrlTm)) && goodsMap.get(matrlTm).size() != 1) {
                v.setIsValid(false);
                v.setMessage("此物料条码,商城存在多个供应商供货");
            }
        }

        ImportShoppingCartResultVo importShoppingCartResultVo = getImportResult(vos);
        apiResponse.setData(importShoppingCartResultVo);

        //保存数据库
        if (importShoppingCartResultVo.getErrCount() <= 0) {

            save(vos, goodsMap);

        }else if (request.getIsContinue().equals("Y") && importShoppingCartResultVo.getSuccessCount() > 0) {

            List<ImportShoppingCartVo> successVoList = vos
                    .stream()
                    .filter( s -> s.getIsValid())
                    .collect(Collectors.toList());

            save(successVoList, goodsMap);

        }else{

            apiResponse.setStatus(ApiResponse.FAILURE);
        }

        return apiResponse;
    }

    @Transactional(rollbackFor = Exception.class)
    private void save(List<ImportShoppingCartVo> vos, Map<String, List<GoodsVo>> goodsVoMap) {

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = new HashMap();
        mapParam.put("userno", loginUserBean.getUserCode());
        List<Tbmqq433Vo> myShoppingCartList = tbmqq433Mapper.getMyShoppingCartList(mapParam);

        //当前用户购物车商品信息
        Map<String, Tbmqq433Vo> myShoppingCartMap = myShoppingCartList
                .stream()
                .collect(Collectors.toMap(Tbmqq433Vo::getMatrltm, t -> t));

        List<Tbmqq433> updateList = new ArrayList();
        List<Tbmqq433> insertList = new ArrayList();

        NowDate nowDate = new NowDate();
        for (ImportShoppingCartVo v: vos) {

            Tbmqq433Vo t = myShoppingCartMap.get(v.getMatrlTm().getValue());
            Tbmqq433 tbmqq433 = new Tbmqq433();


            if (ObjectUtil.isEmpty(t)) {
                GoodsVo goodsVo = goodsVoMap.get(v.getMatrlTm().getValue()).get(0);
                BeanUtils.copyProperties(goodsVo, tbmqq433);
                tbmqq433.setUserno(loginUserBean.getUserCode());
                tbmqq433.setUsername(loginUserBean.getUsername());
                tbmqq433.setCreateuser(loginUserBean.getUserCode());
                tbmqq433.setCreatetime(nowDate.getTimeStr());
                tbmqq433.setCreatedate(nowDate.getDateStr());
                tbmqq433.setUpdateuser(loginUserBean.getUserCode());
                tbmqq433.setUpdatetime(nowDate.getTimeStr());
                tbmqq433.setUpdatedate(nowDate.getDateStr());
                tbmqq433.setQty(NumberUtil.s2bWeight(v.getQty().getValue()));

                insertList.add(tbmqq433);

            } else {

                tbmqq433.setUserno(loginUserBean.getUserCode());
                tbmqq433.setGoodid(t.getGoodid());
                tbmqq433.setQty(NumberUtil.s2bWeight(v.getQty().getValue()));
                tbmqq433.setUpdateuser(loginUserBean.getUserCode());
                tbmqq433.setUpdatetime(nowDate.getTimeStr());
                tbmqq433.setUpdatedate(nowDate.getDateStr());

                updateList.add(tbmqq433);
            }
        }

        if (insertList.size() > 0) {
            batchInsert(insertList,loginUserBean.getUserCode());
        }

        for (Tbmqq433 t: updateList) {
            int updateCount = tbmqq433Mapper.updateByPrimaryKeySelective(t);
            if (updateCount != 1) {
                throw new NetPlusException("购物车商品信息更新错误");
            }
        }
    }

    private Map<String, List<GoodsVo>> getGoodsMap (Set<String> matrlnoSet) {


        Map<String, Object> mapParam = new HashMap();
        mapParam.put("matrltmList", matrlnoSet);
        mapParam.put("statusList", Arrays.asList(
            GoodsStatusEnum.status_25.getCode(),
            GoodsStatusEnum.status_35.getCode(),
            GoodsStatusEnum.status_40.getCode()
        ));

        List<GoodsVo> goodsVoList = tbmqq430Mapper.getGoodsByMatrltmList(mapParam);
        Map<String, List<GoodsVo>> groupData = goodsVoList
                .stream()
                .collect(Collectors.groupingBy(GoodsVo::getMatrltm));

        return groupData;
    }

    private ImportShoppingCartResultVo getImportResult (List<ImportShoppingCartVo> vos) {

        ImportShoppingCartResultVo resultVo = new ImportShoppingCartResultVo();

        List<ImportShoppingCartVo> errVos = vos
                .stream()
                .filter(v -> !v.getIsValid()).collect(Collectors.toList());

        List<Tbmqq433Vo> tbmqq433VoList = errVos.stream().map( e -> {
            Tbmqq433Vo tbmqq433Vo = new Tbmqq433Vo();
            tbmqq433Vo.setMatrltm(e.getMatrlTm().getValue());
            tbmqq433Vo.setQtyStr(e.getQty().getValue());
            tbmqq433Vo.setMessage(String.join(",", e.getMessages()));

            return tbmqq433Vo;
        }).collect(Collectors.toList());

        resultVo.setTotalCount(vos.size());
        resultVo.setErrCount(errVos.size());
        resultVo.setSuccessCount(vos.size() - errVos.size());
        resultVo.setErrInfo(tbmqq433VoList);

        return resultVo;

    }

    public String exportErrInfo (ExportErrInfoRequest request) throws Exception {
        String sheetName = "错误原因明细";
        String templateName = "tmp_shopping_err";
        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(request.getErrInfo());
        genExcelRequest.setSheetName(sheetName);
        genExcelRequest.setTemplateName(templateName);
        String url = exportRest.genExcel(genExcelRequest);
        return url;
    }

    public List<GoodsVo> getSettleGoodInfo (String userno, Set<String> goodIdList) {
        Map<String, Object> map = new HashMap();
        map.put("userno", userno);
        map.put("goodIdList", goodIdList);

        List<GoodsVo> goodsVoList = tbmqq430Mapper.getSettleGoodInfo(map);
        return goodsVoList;
    }

    public int getShoppingCartCount(){
        return tbmqq433Mapper.getShoppingCartCount(identityRest.getCurrentUser().getUserCode());
    }

    public void updateProject (UpdateShoppingCartRequest request) {
        updateShoppingCart(request);
    }

    public void updatePic (UpdateShoppingCartRequest request) {

        if (StringUtils.isEmpty(request.getIsNeedPic())) {
            throw new NetPlusException("是否需要图纸不能为空");
        }

        updateShoppingCart(request);
    }

    public void updateDevice (UpdateShoppingCartRequest request) {
        updateShoppingCart(request);
    }

    public void updateDeviceSimpleNo (UpdateShoppingCartRequest request) {
        updateShoppingCart(request);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateShoppingCart(UpdateShoppingCartRequest request) {

        if (StringUtils.isEmpty(request.getGoodId())) {
            throw new NetPlusException("商品id不能为空");
        }

        GoodsVo goodsVo = tbmqq430Mapper.getGoodsById(request.getGoodId());
        if (ObjectUtil.isEmpty(goodsVo)) {
            throw new NetPlusException("商品信息不存在");
        }

        if (!(goodsVo.getStatus().equals(GoodsStatusEnum.status_25.getCode())
                ||goodsVo.getStatus().equals(GoodsStatusEnum.status_35.getCode())
                ||goodsVo.getStatus().equals(GoodsStatusEnum.status_40.getCode())
        )) {
            throw new NetPlusException("商品未上架");
        }

        if (!checkOverdue(goodsVo.getPopricestartdate(), goodsVo.getPopricedate())) {
            throw new NetPlusException("商品不在协议有效期内，无法操作");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        NowDate nowDate = new NowDate();

        Tbmqq433 tbmqq433 = new Tbmqq433();
        tbmqq433.setGoodid(request.getGoodId());
        tbmqq433.setUserno(loginUserBean.getUserCode());

        tbmqq433.setIsneedpic(request.getIsNeedPic());
        tbmqq433.setProjectname(request.getProjectName());
        tbmqq433.setProjectno(request.getProjectNo());
        tbmqq433.setDevicesimpleno(request.getDeviceSimpleNo());

        if (!StringUtils.isEmpty(tbmqq433.getProjectno())) {

            if (!"SC00".equals(tbmqq433.getProjectno())) {
                tbmqq433.setDeviceapplyno("");
                tbmqq433.setDeviceapplypk("");
            }else{
                tbmqq433.setDeviceapplyno(request.getDeviceApplyNo());
                tbmqq433.setDeviceapplypk(request.getDeviceApplyPk());
            }

        }else{
            tbmqq433.setDeviceapplyno(request.getDeviceApplyNo());
            tbmqq433.setDeviceapplypk(request.getDeviceApplyPk());
        }


        tbmqq433.setUpdatedate(nowDate.getDateStr());
        tbmqq433.setUpdatetime(nowDate.getTimeStr());
        tbmqq433.setUpdateuser(loginUserBean.getUserCode());

        int updateCount = tbmqq433Mapper.updateByPrimaryKeySelective(tbmqq433);
        if (updateCount != 1) {
            throw new NetPlusException("更新失败");
        }
    }

    public void batchUpdateProject (BatchUpdateProjectRequest request) {


        if (ObjectUtil.isEmpty(request.getGoodIdList())) {
            throw new NetPlusException("商品id不能为空");
        }

        List<GoodsVo> goodsVoList = tbmqq430Mapper.getGoodByIds(ObjectUtil.transBeanToMap(request));
        if (ObjectUtil.isEmpty(goodsVoList)) {
            throw new NetPlusException("商品信息不存在");
        }

        for (GoodsVo goodsVo: goodsVoList) {

            if (!(goodsVo.getStatus().equals(GoodsStatusEnum.status_25.getCode())
                    ||goodsVo.getStatus().equals(GoodsStatusEnum.status_35.getCode())
                    ||goodsVo.getStatus().equals(GoodsStatusEnum.status_40.getCode())
            )) {
                throw new NetPlusException(String.format("物料条码%s的商品未上架", goodsVo.getMatrltm()));
            }

            if (!checkOverdue(goodsVo.getPopricestartdate(), goodsVo.getPopricedate())) {
                throw new NetPlusException(String.format("物料条码%s的商品不在协议有效期内，无法操作", goodsVo.getMatrltm()));
            }

        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        NowDate nowDate = new NowDate();

        Tbmqq433Vo tbmqq433Vo = new Tbmqq433Vo();
        tbmqq433Vo.setGoodIdList(request.getGoodIdList());
        tbmqq433Vo.setUserno(loginUserBean.getUserCode());
        tbmqq433Vo.setProjectname(request.getProjectName());
        tbmqq433Vo.setProjectno(request.getProjectNo());

        if (!StringUtils.isEmpty(tbmqq433Vo.getProjectno())) {

            if (!"SC00".equals(tbmqq433Vo.getProjectno())) {
                tbmqq433Vo.setDeviceapplyno("");
                tbmqq433Vo.setDeviceapplypk("");
            }
        }

        tbmqq433Vo.setUpdatedate(nowDate.getDateStr());
        tbmqq433Vo.setUpdatetime(nowDate.getTimeStr());
        tbmqq433Vo.setUpdateuser(loginUserBean.getUserCode());

        int updateCount = tbmqq433Mapper.update(tbmqq433Vo);
        if (updateCount != request.getGoodIdList().size()) {
            throw new NetPlusException("更新失败");
        }
    }

    public Map<String, JK0010SubResponse> checkQty (CheckQtyRequest request) {

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("userno", loginUserBean.getUserCode());

        List<Tbmqq433Vo> tbmqq433VoList = tbmqq433Mapper.getMyShoppingCartList(mapParam);

        Map<String, BigDecimal> result = tbmqq433VoList
                .stream()
                .collect(Collectors.groupingBy(
                            Tbmqq433Vo::getMatrlid,
                            Collectors.mapping(
                                    Tbmqq433Vo::getQty,
                                    Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                            )
                        )
                );


        List<GoodsVo> goodsVoList = new ArrayList();
        for (Map.Entry<String, BigDecimal> entry: result.entrySet()) {

            GoodsVo goodsVo = new GoodsVo();
            goodsVo.setMatrlid(entry.getKey());
            goodsVo.setCartQty(entry.getValue());
            goodsVoList.add(goodsVo);

        }

        return ifaceBiz.getQtyCheckResult(goodsVoList);
    }


    //导出购物车信息
    public String exportMyShoppingCartList(GetMyShoppingCartRequest request){

        String tempName="tmp_shopcart_info";
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("userno", loginUserBean.getUserCode());
        List<Tbmqq433Vo> tbmqq433VoList = tbmqq433Mapper.getMyShoppingCartList(mapParam);
        Set<String> matrlTmSet = tbmqq433VoList
                .stream()
                .map( t -> t.getMatrltm())
                .collect(Collectors.toSet());

        //获取最低价
        if (matrlTmSet.size() > 0) {

            GetPriceCompareRequest getPriceCompareRequest = new GetPriceCompareRequest();
            getPriceCompareRequest.setMatrltmList(new ArrayList(matrlTmSet));
            List<GoodsVo> goodsVoList = goodsListedBiz.getPriceCompare(getPriceCompareRequest);

            Map<String, GoodsVo> goodGroupData = goodsVoList
                    .stream()
                    .collect(Collectors.toMap(
                            g -> g.getMatrltm(),
                            Function.identity(),
                            BinaryOperator.minBy(Comparator.comparing(GoodsVo :: getPrice))
                    ));


            for (Tbmqq433Vo tbmqq433Vo: tbmqq433VoList) {
                String isNeedPic=tbmqq433Vo.getIsneedpic()==null?"无图纸":tbmqq433Vo.getIsneedpic();
                String supplierName=String.format("%s-%s",tbmqq433Vo.getSupplierno(),tbmqq433Vo.getSuppliername());
                tbmqq433Vo.setIsneedpic(isNeedPic);
                tbmqq433Vo.setSuppliername(supplierName);
                if (goodGroupData.keySet().contains(tbmqq433Vo.getMatrltm())) {

                    GoodsVo lowGoodsVo = goodGroupData.get(tbmqq433Vo.getMatrltm());
                    tbmqq433Vo.setLowPrice(lowGoodsVo.getPrice());
                    tbmqq433Vo.setLowQtyUnit(lowGoodsVo.getQtyunit());

                }
            }
        }

        String url="";
        try {
            GenExcelRequest genExcelRequest=new GenExcelRequest();
            genExcelRequest.setItems(tbmqq433VoList);
            genExcelRequest.setTemplateName(tempName);
            genExcelRequest.setSheetName("导出信息");
            url= exportRest.genExcel(genExcelRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return url;
    }

    public ApiResponse erpAddShoppingCart(ErpAddShoppingCartRequest request) {
        //获取需要加入购物车的用户信息
        String userNo = request.getUserNo();
        GetUserInfoRequest userInfoReq = new GetUserInfoRequest();
        userInfoReq.setUserNo(userNo);
        Tbdu01Vo userInfo = identityRest.getUserByUserNo(userInfoReq);
        if(ObjectUtils.isEmpty(userInfo) || !UserTypeEnums.B.getCode().equals(userInfo.getUsertype())){
            return ApiResponse.fail("用户不存在");
        }

        //获取请求加入购物车商品的信息
        List<ErpAddShoppingCartGoodsRequest> requestGoodsList = request.getGoodsList();
        List<String> matrlIds = requestGoodsList.stream().map(e -> e.getMatrlId()).collect(Collectors.toList());
        List<GoodsVo> goods = getGoodsByMatrlIdList(matrlIds);

        if(ObjectUtils.isEmpty(goods)){
            return ApiResponse.fail("商品不存在或已下架");
        }

        NowDate nowDate = new NowDate();

        //商品按照物料ID分组
        Map<String, List<GoodsVo>> collect = goods.stream().collect(Collectors.groupingBy(GoodsVo::getMatrlid));

        //取到每个物料ID价格最低的一条记录
        List<GoodsVo> goodsList = collect.values().stream().map(vos -> {
            if (vos.size() > 1) {
                return vos.stream().min(Comparator.comparing(Tbmqq430::getPrice)).orElse(null);
            } else {
                return vos.get(0);
            }
        }).filter(vo -> !ObjectUtils.isEmpty(vo)).collect(Collectors.toList());

        //获取最低记录的ID
        List<String> goodsIds = goodsList.stream().map(vo -> vo.getGoodid()).collect(Collectors.toList());

        //根据ID获取购物车中数据
        Map<String, Object> getMyShoppingCartListMap = ObjectUtil.transBeanToMap(request);
        getMyShoppingCartListMap.put("userno", userNo);
        getMyShoppingCartListMap.put("goodIdList", goodsIds);
        List<Tbmqq433Vo> shoppingCartList = tbmqq433Mapper.getMyShoppingCartList(getMyShoppingCartListMap);
        List<String> shoppingCartIds = shoppingCartList.stream().map(Tbmqq433Vo::getGoodid).collect(Collectors.toList());

        //构造需要加入购物车中的数据
        List<Tbmqq433> insertList = new ArrayList<>();
        for (GoodsVo goodsVo : goodsList) {
            if(shoppingCartIds.contains(goodsVo.getGoodid())){
                continue;
            }
            Tbmqq433 tbmqq433 = new Tbmqq433();
            BeanUtils.copyProperties(goodsVo, tbmqq433);

            BigDecimal requestQty = BigDecimal.ONE;

            if (ObjectUtil.nonEmpty(goodsVo.getMinbuyqty())
                    && NumberUtil.gt(goodsVo.getMinbuyqty(), NumberUtil.ZORE)) {
                requestQty=goodsVo.getMinbuyqty();
            }

            if (!checkOverdue(goodsVo.getPopricestartdate(), goodsVo.getPopricedate())) {
                return ApiResponse.fail("不在协议有效期内，无法加入购物车");
            }

            tbmqq433.setQty(requestQty);
            tbmqq433.setUserno(userInfo.getUserno());
            tbmqq433.setUsername(userInfo.getName());
            tbmqq433.setCreatedate(nowDate.getDateStr());
            tbmqq433.setCreatetime(nowDate.getTimeStr());
            tbmqq433.setCreateuser(userInfo.getUserno());
            tbmqq433.setUpdateuser(userInfo.getUserno());
            tbmqq433.setUpdatetime(nowDate.getTimeStr());
            tbmqq433.setUpdatedate(nowDate.getDateStr());
            insertList.add(tbmqq433);
        }


        if (insertList.size() > 0) {
            batchInsert(insertList,userInfo.getUserno());
        }

        Map<String, Integer> result = new HashMap();
        result.put("invalidNum", requestGoodsList.size() - goodsList.size());
        result.put("addNum", insertList.size());
        result.put("existNum", shoppingCartList.size());

        return ApiResponse.success(result);
    }


    private List<GoodsVo> getGoodsByMatrlIdList(List<String> ids){
        Map<String, Object> mapParam = new HashMap();
        mapParam.put("matrlIdList", ids);
        mapParam.put("statusList", Arrays.asList(
                GoodsStatusEnum.status_25.getCode(),
                GoodsStatusEnum.status_35.getCode(),
                GoodsStatusEnum.status_40.getCode()
        ));

        return tbmqq430Mapper.getGoodByMatrlIdList(mapParam);
    }

    private void batchInsert(List<Tbmqq433> list,String userNo){
        List<String> goodsIds = list.stream().map(Tbmqq433::getGoodid).collect(Collectors.toList());
        Map<String, Object> mapParam = new HashMap();
        mapParam.put("userno", userNo);
        mapParam.put("goodIdList", goodsIds);
        List<GoodCollectVo> remarks=tbmqq434Mapper.getMyCollectListRemark(mapParam);
        Map<String, String> remarkMap = remarks.stream().collect(Collectors.toMap(GoodCollectVo::getGoodid, t -> org.springframework.util.StringUtils.isEmpty(t.getRemark())?"":t.getRemark(), (t1, t2) -> t1));
        List<Tbmqq433> insertList = list.stream().map(t -> {
            t.setRemark(remarkMap.get(t.getGoodid()));
            return t;
        }).collect(Collectors.toList());
        tbmqq433Mapper.batchInsert(insertList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateShoppingCartRemark(UpdateShoppingCartRemarkRequest request) {
        if(org.springframework.util.StringUtils.isEmpty(request.getGoodId())){
            throw new NetPlusException("商品ID不能为空!");
        }
        if(!org.springframework.util.StringUtils.isEmpty(request.getRemark()) && request.getRemark().length()>500){
            throw new NetPlusException("备注超过上限!");
        }
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("userNo", loginUserBean.getUserCode());
        tbmqq433Mapper.updateShoppingCartRemark(mapParam);
    }
}
