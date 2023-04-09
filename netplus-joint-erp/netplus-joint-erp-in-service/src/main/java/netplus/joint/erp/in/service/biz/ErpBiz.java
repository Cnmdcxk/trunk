package netplus.joint.erp.in.service.biz;

import com.google.gson.Gson;
import netplus.joint.erp.api.enums.JK0025ResponseCodeEnum;
import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.enums.YesNoEnum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.BaseResponse;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.component.entity.response.ApiResponse;
import netplus.joint.erp.api.request.in.JK0008.JK0008Request;
import netplus.joint.erp.api.request.in.JK0008.JK0008SubRequest;
import netplus.joint.erp.api.request.in.JK0023Request;
import netplus.joint.erp.api.request.in.JK0025Request;
import netplus.joint.erp.api.request.in.JK0026.JK0026Request;
import netplus.joint.erp.api.request.out.JK0014Request;
import netplus.joint.erp.api.request.out.JK0027Request;
import netplus.joint.erp.api.request.out.JK0028Request;
import netplus.joint.erp.api.response.in.*;
import netplus.joint.erp.api.response.in.JK0008.JK0008Response;
import netplus.joint.erp.api.response.out.JK0027Response;
import netplus.joint.erp.api.response.out.JK0028.JK0028Response;
import netplus.joint.erp.api.response.out.JK0028.JK0028SubResponse;
import netplus.joint.erp.api.rest.ErpOutRest;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.api.request.basicData.GetMatrlByIdsAndSupplierNoRequest;
import netplus.mall.api.request.good.BatchInsertGoodRequest;
import netplus.mall.api.request.order.OrderDetailCancelRequest;
import netplus.mall.api.request.picLib.GetPicByMatrlTmAndSupplierNoRequest;
import netplus.mall.api.request.shoppingCart.erpAdd.ErpAddShoppingCartGoodsRequest;
import netplus.mall.api.request.shoppingCart.erpAdd.ErpAddShoppingCartRequest;
import netplus.mall.api.rest.*;
import netplus.mall.api.vo.Tbmqq405Vo;
import netplus.mall.api.vo.Tbmqq407Vo;
import netplus.mall.api.vo.Tbmqq435Vo;
import netplus.mall.api.vo.picLib.Tbmqq429Vo;
import netplus.provider.api.enums.EnumLoginCode;
import netplus.provider.api.enums.UserTypeEnums;
import netplus.provider.api.pojo.ygmalluser.Tbdu01;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.request.GetSupplierNoRequest;
import netplus.provider.api.request.LoginRequest;
import netplus.provider.api.request.SyncProviderRequest;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.rest.ProvideRest;
import netplus.provider.api.rest.ServiceConfigRest;
import netplus.provider.api.vo.LoginResult;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.utils.date.DateUtil;
import netplus.utils.date.NowDate;
import netplus.utils.number.NumberUtil;
import netplus.utils.object.ObjectUtil;
import netplus.utils.pager.Pager;
import netplus.utils.uuid.UuidUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ErpBiz {

    @Autowired
    private BasicDataRest basicDataRest;

    @Autowired
    private ProvideRest provideRest;

    @Autowired
    private ServiceConfigRest serviceConfigRest;

    @Autowired
    private PicLibRest picLibRest;

    @Autowired
    private GoodsRest goodsRest;

    @Autowired
    private OrderRest orderRest;

    @Autowired
    private IdentityRest identityRest;

    @Autowired
    private ShoppingCartRest shoppingCartRest;

    @Autowired
    private ErpOutRest erpOutRest;

    @Autowired
    private FutureGoodRest futureGoodRest;


    public BaseResponse<JK0008Response> contractFrameworkChange(BaseRequest<JK0008Request> request){

        try {
            NowDate nowDate = new NowDate();
            List<Tbmqq430> tbmqq430List = new ArrayList();
            List<Tbmqq435Vo> tbmqq435List = new ArrayList();
            JK0008Request jk0008Request = request.getReqData();
            String supplierNo = request.getReqData().getSupplierNo();

            GetSupplierNoRequest getSupplierNoRequest = new GetSupplierNoRequest();
            getSupplierNoRequest.setSupplierNo(supplierNo);
            Tbdu01Vo tbdu01Vo = provideRest.getSupplierNo(getSupplierNoRequest);
            if (ObjectUtil.isEmpty(tbdu01Vo)) {
                throw new NetPlusException(String.format("供应商：%s未同步", supplierNo));
            }

            if (ObjectUtil.nonEmpty(jk0008Request)) {
                for (JK0008SubRequest j: jk0008Request.getDetail()) {

                    if (StringUtils.isEmpty(j.getWzcgr())
                            || StringUtils.isEmpty(j.getWzcgrgh())
                            || StringUtils.isEmpty(j.getWzcgrlxfs())) {
                        throw new NetPlusException(String.format("协议号：%s， 物资条码：%s，物资采购人信息不能为空", jk0008Request.getPono(), j.getWzbm()));
                    }
                }
            }

            List<String> matrlIdList = jk0008Request
                    .getDetail()
                    .stream().map( t -> t.getWzmcbmPk())
                    .collect(Collectors.toList());

            List<String> matrlTmList = jk0008Request
                    .getDetail()
                    .stream().map( t -> t.getWztm())
                    .collect(Collectors.toList());


            //分页获取，因为数据可能超过1000条
            Pager<String> matrlIdPage = new Pager(matrlIdList, 1000);
            List<Tbmqq405Vo> tbmqq405VoList = new ArrayList();
            for (int i=1; i<=matrlIdPage.getTotalPage(); i++) {
                GetMatrlByIdsAndSupplierNoRequest getMatrlByIdsAndSupplierNoRequest = new GetMatrlByIdsAndSupplierNoRequest();
                getMatrlByIdsAndSupplierNoRequest.setMatrlIdList(matrlIdPage.getPageData(i));
                getMatrlByIdsAndSupplierNoRequest.setSupplierNo(jk0008Request.getSupplierNo());
                List<Tbmqq405Vo> subTbmqq405VoList = basicDataRest.getMatrlByIdsAndSupplierNo(getMatrlByIdsAndSupplierNoRequest);

                if (ObjectUtil.nonEmpty(subTbmqq405VoList)) {
                    tbmqq405VoList.addAll(subTbmqq405VoList);
                }
            }

            if (matrlIdList.size() != tbmqq405VoList.size()) {

                List<String> existMatrlTmSList = tbmqq405VoList
                        .stream()
                        .map( m -> m.getMatrltm())
                        .collect(Collectors.toList());

                matrlTmList.removeAll(existMatrlTmSList);
                throw new NetPlusException("部分物料分类关系不存在: %s", new Gson().toJson(matrlTmList));
            }

            //获取第三方供应商配置
            Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_OUT_SUPPLIER.code);
            if (tbmqq461.getVal().equals(jk0008Request.getSupplierNo())) {
                for (Tbmqq405Vo tbmqq405Vo: tbmqq405VoList) {
                    if (StringUtils.isEmpty(tbmqq405Vo.getThrplatgoodno())) {
                        throw new NetPlusException(String.format("物料id: %s, sku关系未维护", tbmqq405Vo.getMatrlid()));
                    }
                }
            }

            Map<String, Tbmqq405Vo> tbmqq405VoMap = tbmqq405VoList
                    .stream()
                    .collect(Collectors.toMap(Tbmqq405Vo::getMatrlid, t -> t));

            //获取供应商自己维护的图片
            Pager<String> matrlTmPage = new Pager(matrlTmList, 1000);
            List<Tbmqq429Vo> tbmqq429VoList = new ArrayList();
            for (int i=1; i<=matrlTmPage.getTotalPage(); i++) {
                GetPicByMatrlTmAndSupplierNoRequest getPicByMatrlTmAndSupplierNoRequest = new GetPicByMatrlTmAndSupplierNoRequest();
                getPicByMatrlTmAndSupplierNoRequest.setSupplierNo(jk0008Request.getSupplierNo());
                getPicByMatrlTmAndSupplierNoRequest.setMatrlTmList(matrlTmPage.getPageData(i));
                getPicByMatrlTmAndSupplierNoRequest.setDeleted("N");
                List<Tbmqq429Vo> subTbmqq429VoList = picLibRest.getPicByMatrlTmAndSupplierNo(getPicByMatrlTmAndSupplierNoRequest);

                if (ObjectUtil.nonEmpty(subTbmqq429VoList)) {
                    tbmqq429VoList.addAll(subTbmqq429VoList);
                }
            }
            Map<String, List<Tbmqq429Vo>> tbmqq429VoMap = tbmqq429VoList.stream().collect(Collectors.groupingBy( t -> t.getMatrltm()));

            //获取公共图片
            Pager<String> matrlTmPage2 = new Pager(matrlTmList, 1000);
            List<Tbmqq407Vo> tbmqq407VoList = new ArrayList();
            for (int i=1; i<=matrlTmPage2.getTotalPage(); i++) {
                List<Tbmqq407Vo> subTbmqq407VoList = picLibRest.getPicByMatrlTm(matrlTmPage2.getPageData(i));

                if (ObjectUtil.nonEmpty(subTbmqq407VoList)) {
                    tbmqq407VoList.addAll(subTbmqq407VoList);
                }
            }
            Map<String, List<Tbmqq407Vo>> tbmqq407VoMap = tbmqq407VoList.stream().collect(Collectors.groupingBy( t -> t.getMatrltm()));

            Date startPopData = DateUtil.valueOf(jk0008Request.getStartPopriceDatetime(), "yyyy-MM-dd HH:mm:ss");
            Date endPopData = DateUtil.valueOf(jk0008Request.getEndPopriceDatetime(), "yyyy-MM-dd HH:mm:ss");

            for (JK0008SubRequest jk0008SubRequest: jk0008Request.getDetail()) {


                Tbmqq405Vo tbmqq405Vo = tbmqq405VoMap.get(jk0008SubRequest.getWzmcbmPk());

                Tbmqq430 tbmqq430 = new Tbmqq430();
                tbmqq430.setPono(jk0008Request.getPono());
                tbmqq430.setPonopk(jk0008Request.getPonoPk());
                tbmqq430.setPopricestartdate(DateUtil.format(startPopData, "yyyyMMdd"));
                tbmqq430.setPopricestarttime(DateUtil.format(startPopData, "HHmmss"));
                tbmqq430.setPopricedate(DateUtil.format(endPopData, "yyyyMMdd"));
                tbmqq430.setPopricetime(DateUtil.format(endPopData, "HHmmss"));
                tbmqq430.setTax(jk0008Request.getRate());
                tbmqq430.setSupplierno(jk0008Request.getSupplierNo());
                tbmqq430.setSuppliername(jk0008Request.getSupplierName());
                tbmqq430.setSupplierpk(jk0008Request.getSupplierPk());

                tbmqq430.setMatrlid(jk0008SubRequest.getWzmcbmPk());
                tbmqq430.setMatrltm(jk0008SubRequest.getWztm());
                tbmqq430.setMatrlno(jk0008SubRequest.getWzbm());
                tbmqq430.setProductname(jk0008SubRequest.getWzmc());
                tbmqq430.setSpec(jk0008SubRequest.getGgxh());
                tbmqq430.setQtyunit(jk0008SubRequest.getJldw());
                tbmqq430.setHaspic(jk0008SubRequest.getHasPic());
                tbmqq430.setAgent(jk0008SubRequest.getWzcgr());

                tbmqq430.setGoodno(tbmqq405Vo.getThrplatgoodno());
                tbmqq430.setCategorypk(tbmqq405Vo.getCategorypk());
                tbmqq430.setCategoryname(tbmqq405Vo.getCategoryname());
                tbmqq430.setOnelevelclapk(tbmqq405Vo.getOnelevelclapk());
                tbmqq430.setOnelevelclaname(tbmqq405Vo.getOnelevelclaname());
                tbmqq430.setTwolevelclapk(tbmqq405Vo.getTwolevelclapk());
                tbmqq430.setTwolevelclaname(tbmqq405Vo.getTwolevelclaname());
                tbmqq430.setLeadtimenum(jk0008SubRequest.getJhzq().longValue());
                tbmqq430.setAgentno(jk0008SubRequest.getWzcgrgh());
                tbmqq430.setAgentphone(jk0008SubRequest.getWzcgrlxfs());

                if (ObjectUtil.nonEmpty(jk0008SubRequest.getPrice())) {
                    tbmqq430.setPrice(jk0008SubRequest.getPrice());
                    tbmqq430.setOriginprice(jk0008SubRequest.getPrice());
                    tbmqq430.setNotaxprice(NumberUtil.noTaxPrice(jk0008SubRequest.getPrice(), jk0008Request.getRate()));
                }

                if (tbmqq429VoMap.keySet().contains(tbmqq430.getMatrltm())) {

                    List<Tbmqq429Vo> existTbmqq429Vo = tbmqq429VoMap.get(tbmqq430.getMatrltm());
                    existTbmqq429Vo.forEach( t -> {

                        Tbmqq435Vo tbmqq435Vo = new Tbmqq435Vo();
                        tbmqq435Vo.setSupplierno(tbmqq430.getSupplierno());
                        tbmqq435Vo.setPicturename(t.getPicturename());
                        tbmqq435Vo.setPicturenum(t.getPicturenum());
                        tbmqq435Vo.setPictureurl(t.getPictureurl());
                        tbmqq435Vo.setCreatedate(nowDate.getDateStr());
                        tbmqq435Vo.setCreatetime(nowDate.getTimeStr());
                        tbmqq435Vo.setCreateuser(SysCodeEnum.CK.code);
                        tbmqq435Vo.setUpdatedate(nowDate.getDateStr());
                        tbmqq435Vo.setUpdatetime(nowDate.getTimeStr());
                        tbmqq435Vo.setUpdateuser(SysCodeEnum.CK.code);
                        tbmqq435Vo.setMatrltm(tbmqq430.getMatrltm());
                        tbmqq435Vo.setMatrlid(tbmqq430.getMatrlid());
                        tbmqq435List.add(tbmqq435Vo);
                    });

                    tbmqq430.setPictureurl(existTbmqq429Vo.get(0).getPictureurl());

                }else {

                    if (tbmqq407VoMap.keySet().contains(tbmqq430.getMatrltm())) {

                        List<Tbmqq407Vo> existTbmqq407Vo = tbmqq407VoMap.get(tbmqq430.getMatrltm());
                        existTbmqq407Vo.forEach( t -> {

                            Tbmqq435Vo tbmqq435Vo = new Tbmqq435Vo();
                            tbmqq435Vo.setSupplierno(tbmqq430.getSupplierno());
                            tbmqq435Vo.setPicturename(t.getPicturename());
                            tbmqq435Vo.setPicturenum(t.getPicturenum());
                            tbmqq435Vo.setPictureurl(t.getPictureurl());
                            tbmqq435Vo.setCreatedate(nowDate.getDateStr());
                            tbmqq435Vo.setCreatetime(nowDate.getTimeStr());
                            tbmqq435Vo.setCreateuser(SysCodeEnum.CK.code);
                            tbmqq435Vo.setUpdatedate(nowDate.getDateStr());
                            tbmqq435Vo.setUpdatetime(nowDate.getTimeStr());
                            tbmqq435Vo.setUpdateuser(SysCodeEnum.CK.code);
                            tbmqq435Vo.setMatrltm(tbmqq430.getMatrltm());
                            tbmqq435Vo.setMatrlid(tbmqq430.getMatrlid());
                            tbmqq435List.add(tbmqq435Vo);
                        });

                        tbmqq430.setPictureurl(existTbmqq407Vo.get(0).getPictureurl());

                    }
                }

                tbmqq430List.add(tbmqq430);
            }

            if(nowDate.getNow().compareTo(startPopData)<0){
                futureGoodRest.batchImport(tbmqq430List);
                return BaseResponse.fail(request.getReqId(),  "尚未到达协议有效期,已存入中间表!");
            }

            BatchInsertGoodRequest batchInsertGoodRequest = new BatchInsertGoodRequest();
            batchInsertGoodRequest.setTbmqq430List(tbmqq430List);
            batchInsertGoodRequest.setTbmqq435List(tbmqq435List);

            goodsRest.batchInsert(batchInsertGoodRequest);

            return BaseResponse.success(request.getReqId());

        }catch (Exception e) {
            return BaseResponse.fail(request.getReqId(),  ExceptionUtils.getFullStackTrace(e));
        }

    }

    public BaseResponse<JK0014Response> syncProvider(BaseRequest<List<JK0014Request>> request){

        try {

            NowDate nowDate = new NowDate();

            JK0014Response jk0014Response = new JK0014Response();
            List<String> supplierPkList = new ArrayList();
            List<JK0014Request> jk0014RequestList = request.getReqData();
            List<Tbdu01> tbdu01List = jk0014RequestList.stream().map(q -> {

                Tbdu01 tbdu01 = new Tbdu01();

                tbdu01.setComppk(q.getSupplierPk());
                tbdu01.setCompno(q.getSupplierNo());
                tbdu01.setCompname(q.getSupplierName());
                tbdu01.setUserno(String.format("M%s", q.getSupplierNo()));
                tbdu01.setName(q.getSupplierName());

                tbdu01.setUsertype(UserTypeEnums.S.getCode());
                tbdu01.setPhone(q.getPhone());
                tbdu01.setIsavailable(YesNoEnum.Yes.getValue());
                tbdu01.setIsrole(YesNoEnum.Yes.getValue());

                tbdu01.setPassword("11111111");
                tbdu01.setCreateuser(SysCodeEnum.CK.code);
                tbdu01.setCreatedate(nowDate.getDateTimeStr3());
                tbdu01.setModifyuser(SysCodeEnum.CK.code);
                tbdu01.setModifydate(nowDate.getDateTimeStr3());

                supplierPkList.add(q.getSupplierPk());

                return tbdu01;

            }).collect(Collectors.toList());

            SyncProviderRequest syncProviderRequest = new SyncProviderRequest();
            syncProviderRequest.setTbdu01VoList(tbdu01List);
            provideRest.syncProvider(syncProviderRequest);

            jk0014Response.setSupplierPk(supplierPkList);
            return BaseResponse.success(request.getReqId(), jk0014Response);

        }catch (Exception e) {


            return BaseResponse.fail(request.getReqId(), ExceptionUtils.getFullStackTrace(e));
        }

    }

    public BaseResponse<JK0023Response> JK0023 (BaseRequest<JK0023Request> request) {

        try {

            OrderDetailCancelRequest orderDetailCancelRequest = new OrderDetailCancelRequest();
            orderDetailCancelRequest.setOrderDetailId(request.getReqData().getScddmxbm_pk());

            orderRest.orderDetailCancel(orderDetailCancelRequest);
            return BaseResponse.success(request.getReqId());

        } catch (Exception e) {
            return BaseResponse.fail(request.getReqId(), ExceptionUtils.getFullStackTrace(e));
        }
    }


    public BaseResponse<JK0025Response> JK0025 (BaseRequest<JK0025Request> request) {
        try {
            if (StringUtils.isEmpty(request.getReqData().getCode())) {
                return jk0025RespFailHasCode(request.getReqId(), JK0025ResponseCodeEnum.REQUEST_PARAM_ERROR, "code不能为空");
            }

            String source = request.getReqData().getSource();
            if(StringUtils.isEmpty(source)){
                return jk0025RespFailHasCode(request.getReqId(), JK0025ResponseCodeEnum.REQUEST_PARAM_ERROR,  "客户端类型不能为空");
            }else{
                source=source.toUpperCase();
            }

            if (!"APP".equals(source) && !"PC".equals(source)) {
                return jk0025RespFailHasCode(request.getReqId(), JK0025ResponseCodeEnum.REQUEST_PARAM_ERROR,  "客户端类型必须是APP或PC");
            }

            //通过code获取token
            JK0027Request jk0027Request = new JK0027Request();
            jk0027Request.setCode(request.getReqData().getCode());

            BaseRequest<JK0027Request> jk0027BaseRequest = new BaseRequest<>();
            jk0027BaseRequest.setReqId(UuidUtil.getUuid());
            jk0027BaseRequest.setReqTime(String.valueOf(new Date().getTime()));
            jk0027BaseRequest.setReqData(jk0027Request);
            BaseResponse<JK0027Response> jk0027Resp = erpOutRest.JK0027(jk0027BaseRequest);
            if (jk0027Resp.getStatus().equals("0")) {
                return jk0025RespFailHasCode(request.getReqId(), JK0025ResponseCodeEnum.OTHER_ERROR, jk0027Resp.getMessage());
            }
            String erpToken = jk0027Resp.getRespData().getResult();
            if(StringUtils.isEmpty(erpToken)){
                return jk0025RespFailHasCode(request.getReqId(), JK0025ResponseCodeEnum.INTERFACE_RESP_DATA_NULL,"JK0027");
            }


            //通过token获取用户信息
            JK0028Request jk0028Request = new JK0028Request();
            jk0028Request.setToken(erpToken);

            BaseRequest<JK0028Request> jk0028BaseRequest = new BaseRequest<>();
            jk0028BaseRequest.setReqId(UuidUtil.getUuid());
            jk0028BaseRequest.setReqTime(String.valueOf(new Date().getTime()));
            jk0028BaseRequest.setReqData(jk0028Request);
            BaseResponse<JK0028Response> jk0028Resp = erpOutRest.JK0028(jk0028BaseRequest);
            if (jk0028Resp.getStatus().equals("0")) {
                return jk0025RespFailHasCode(request.getReqId(), JK0025ResponseCodeEnum.OTHER_ERROR, jk0028Resp.getMessage());
            }
            JK0028SubResponse userInfo = jk0028Resp.getRespData().getResult();

            if(ObjectUtil.isEmpty(userInfo) || StringUtils.isEmpty(userInfo.getUserCode())){
                return jk0025RespFailHasCode(request.getReqId(), JK0025ResponseCodeEnum.INTERFACE_RESP_DATA_NULL,"JK0028");
            }

            String userName=userInfo.getUserCode();
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setLoginMethod("Q");
            loginRequest.setUsername(userName);
            loginRequest.setSource(source);
            LoginResult loginResult = identityRest.doLogin(loginRequest);


            if (EnumLoginCode.OK.getValue().equals(loginResult.getCode())) {
                JK0025Response jk0025Response = new JK0025Response();
                jk0025Response.setAuthToken(loginResult.getAuthToken());
                ConfigureEnum mallHost = ConfigureEnum.MALL_HOST;
                if("APP".equals(source)){
                    mallHost=ConfigureEnum.MALL_HOST_MOBILE;
                }
                Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(mallHost.code);
                String redirectUrl="https://"+tbmqq461.getVal()+"?AuthToken="+loginResult.getAuthToken();
                jk0025Response.setRedirectUrl(redirectUrl);

                return BaseResponse.success(request.getReqId(), jk0025Response);
            }else if(EnumLoginCode.NotExists.getValue().equals(loginResult.getCode())){
                return jk0025RespFailHasCode(request.getReqId(), JK0025ResponseCodeEnum.USER_NOT_EXISTS);
            }else{
                return jk0025RespFailHasCode(request.getReqId(), JK0025ResponseCodeEnum.OTHER_ERROR, loginResult.getMsg());
            }
        } catch (Exception e) {
            return jk0025RespFailHasCode(request.getReqId(), JK0025ResponseCodeEnum.UNKNOWN_ERROR, ExceptionUtils.getFullStackTrace(e));
        }
    }

    private BaseResponse<JK0025Response> jk0025RespFailHasCode(String reqId, JK0025ResponseCodeEnum respCode){
        return jk0025RespFailHasCode(reqId, respCode,null);
    }

    private BaseResponse<JK0025Response> jk0025RespFailHasCode(String reqId, JK0025ResponseCodeEnum respCode, String message){
        JK0025Response jk0025Response = new JK0025Response();
        jk0025Response.setCode(respCode.getCode());
        if(StringUtils.isEmpty(message)){
            jk0025Response.setMessage(respCode.getMessage());
        }else{
            jk0025Response.setMessage(String.format("%s:%s",respCode.getMessage(),message));
        }
        return BaseResponse.fail(reqId,"",jk0025Response);
    }

    public BaseResponse<JK0026Response> JK0026(BaseRequest<JK0026Request> request) {
        try {
            JK0026Request reqData = request.getReqData();
            ErpAddShoppingCartRequest req = new ErpAddShoppingCartRequest();
            req.setUserNo(reqData.getUserNo());
            List<ErpAddShoppingCartGoodsRequest> goodsList = reqData.getGoodsList().stream().map(e -> {
                ErpAddShoppingCartGoodsRequest erpAddShoppingCartGoodsRequest = new ErpAddShoppingCartGoodsRequest();
                erpAddShoppingCartGoodsRequest.setMatrlId(e.getMatrlId());
                return erpAddShoppingCartGoodsRequest;
            }).collect(Collectors.toList());
            req.setGoodsList(goodsList);

            ApiResponse shoppingCartResponse = shoppingCartRest.erpAddShoppingCart(req);
            if(ApiResponse.SUCCESS==shoppingCartResponse.getStatus()){
                Map<String, Integer> data = (Map<String, Integer>) shoppingCartResponse.getData();
                JK0026Response response = new JK0026Response();
                response.setInvalidNum(data.get("invalidNum"));
                response.setAddNum(data.get("addNum"));
                response.setExistNum(data.get("existNum"));
                return BaseResponse.success(request.getReqId(),response);
            }else{
                return BaseResponse.fail(request.getReqId(), shoppingCartResponse.getMsg());
            }
        }catch (Exception e){
            return BaseResponse.fail(request.getReqId(), ExceptionUtils.getFullStackTrace(e));
        }
    }

}
