package netplus.mall.service.biz;

import com.google.gson.Gson;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.enums.OneOrZeroEnum;
import netplus.component.entity.enums.YesNoEnum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.BaseResponse;
import netplus.joint.erp.api.request.out.*;
import netplus.joint.erp.api.request.out.JK0010.JK0010Request;
import netplus.joint.erp.api.request.out.JK0010.JK0010SubRequest;
import netplus.joint.erp.api.request.out.JK0011.JK0011Request;
import netplus.joint.erp.api.request.out.JK0011.JK0011SubRequest;
import netplus.joint.erp.api.request.out.JK0012.JK0012Request;
import netplus.joint.erp.api.request.out.JK0018.JK0018Request;
import netplus.joint.erp.api.request.out.JK0018.JK0018SubRequest;
import netplus.joint.erp.api.request.out.JK0032.JK0032Request;
import netplus.joint.erp.api.request.out.JK0032.JK0032SubRequest;
import netplus.joint.erp.api.response.out.JK0004.JK0004Response;
import netplus.joint.erp.api.response.out.JK0004.JK0004SubResponse;
import netplus.joint.erp.api.response.out.JK0005.JK0005Response;
import netplus.joint.erp.api.response.out.JK0005.JK0005SubResponse;
import netplus.joint.erp.api.response.out.JK0006.JK0006Response;
import netplus.joint.erp.api.response.out.JK0006.JK0006SubResponse;
import netplus.joint.erp.api.response.out.*;
import netplus.joint.erp.api.response.out.JK0010.JK0010Response;
import netplus.joint.erp.api.response.out.JK0010.JK0010SubResponse;
import netplus.joint.erp.api.response.out.JK0012.JK0012Response;
import netplus.joint.erp.api.response.out.JK0012.JK0012SubResponse;
import netplus.joint.erp.api.response.out.JK0015.JK0015Response;
import netplus.joint.erp.api.response.out.JK0015.JK0015SubResponse;
import netplus.joint.erp.api.response.out.JK0016.JK0016Response;
import netplus.joint.erp.api.response.out.JK0016.JK0016SprOneResponse;
import netplus.joint.erp.api.response.out.JK0016.JK0016SprTwoResponse;
import netplus.joint.erp.api.response.out.JK0016.JK0016SubResponse;
import netplus.joint.erp.api.response.out.JK0017.JK0017Response;
import netplus.joint.erp.api.response.out.JK0017.JK0017SubResponse;
import netplus.joint.erp.api.response.out.JK0017.NodeList;
import netplus.joint.erp.api.response.out.JK0017.WorkFlowList;
import netplus.joint.erp.api.response.out.JK0022.JK0022Response;
import netplus.joint.erp.api.rest.ErpOutRest;
import netplus.joint.zkh.api.pojo.GoodsPriceVo;
import netplus.joint.zkh.api.pojo.SubmitOrderVo;
import netplus.joint.zkh.api.request.out.InvoiceInfo;
import netplus.joint.zkh.api.request.out.Sku_out;
import netplus.joint.zkh.api.request.out.SubmitOrderRequest;
import netplus.joint.zkh.api.rest.ZkhOutRest;
import netplus.mall.api.enums.AddrTypeEnum;
import netplus.mall.api.pojo.ygmalluser.Approve;
import netplus.mall.api.request.iface.GetGoodLatestPriceRequest;
import netplus.mall.api.request.mall.GetDeviceRequest;
import netplus.mall.api.request.mall.GetProjectRequest;
import netplus.mall.api.request.order.GetSprInfoRequest;
import netplus.mall.api.request.order.ViewApproveProgressRequest;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.api.vo.iface.GoodLatestPriceVo;
import netplus.mall.api.vo.order.Tbmqq440Vo;
import netplus.mall.api.vo.order.Tbmqq441Vo;
import netplus.mall.service.dao.*;
import netplus.messaging.api.rest.MessagingRest;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.rest.CommonRest;
import netplus.provider.api.rest.ServiceConfigRest;
import netplus.utils.date.DateUtil;
import netplus.utils.date.NowDate;
import netplus.utils.number.NumberUtil;
import netplus.utils.object.ObjectUtil;
import netplus.utils.uuid.UuidUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IfaceBiz {

    private Log logger = LogFactory.getLog(IfaceBiz.class);


    @Autowired
    private ErpOutRest erpOutRest;

    @Autowired
    private ZkhOutRest zkhOutRest;

    @Autowired
    Tbmqq440Mapper tbmqq440Mapper;

    @Autowired
    Tbmqq441Mapper tbmqq441Mapper;

    @Autowired
    Tbmqq430Mapper tbmqq430Mapper;

    @Autowired
    MessagingRest messagingRest;

    @Autowired
    CommonRest commonRest;

    @Autowired
    ServiceConfigRest serviceConfigRest;

    @Autowired
    Tbmqq436Mapper tbmqq436Mapper;

    @Autowired
    ApproveMapper approveMapper;


    public List<GoodLatestPriceVo> getGoodLatestPrice (GetGoodLatestPriceRequest request) {


        NowDate nowDate = new NowDate();

        List<GoodLatestPriceVo> goodLatestPriceVoList = new ArrayList();
        if (YesNoEnum.Yes.getValue().equals(request.getIsMallOutProvider())) {


            List<GoodsPriceVo> goodsPriceVoList = zkhOutRest.getSellPrice(new ArrayList(request.getMatrlIdAndSkuMap().values()));

            if (ObjectUtil.isEmpty(goodsPriceVoList)) {
                throw new NetPlusException(String.format("震坤行价格查询数据不存在"));

            }

            for (GoodsPriceVo goodsPriceVo: goodsPriceVoList) {
                GoodLatestPriceVo goodLatestPriceVo = new GoodLatestPriceVo();
                goodLatestPriceVo.setMatrlIdOrSku(goodsPriceVo.getSkuId());
                goodLatestPriceVo.setPrice(NumberUtil.d2bPrice(goodsPriceVo.getPrice()));
                goodLatestPriceVoList.add(goodLatestPriceVo);
            }




        }else{

            JK0015Request jk0015Request = new JK0015Request();
            jk0015Request.setGypbm_pk(request.getPonopk());
            jk0015Request.setWzmcbm_pk_list(new ArrayList(request.getMatrlIdAndSkuMap().keySet()));
            BaseRequest<JK0015Request> baseRequest = new BaseRequest();
            baseRequest.setReqId(UuidUtil.getUuid());
            baseRequest.setReqTime(String.valueOf(new Date().getTime()));
            baseRequest.setReqData(jk0015Request);

            BaseResponse<JK0015Response> resp = erpOutRest.JK0015(baseRequest);

            if (OneOrZeroEnum.One.getValue().equals(resp.getStatus())) {

                if (ObjectUtil.isEmpty(resp.getRespData().getData())) {
                    throw new NetPlusException(String.format("仓库价格查询数据不存在"));
                }

                for (JK0015SubResponse jk0015SubResponse: resp.getRespData().getData()) {
                    GoodLatestPriceVo goodLatestPriceVo = new GoodLatestPriceVo();
                    goodLatestPriceVo.setMatrlIdOrSku(jk0015SubResponse.getWzmcbm_pk());
                    goodLatestPriceVo.setPrice(jk0015SubResponse.getHsdj());
                    goodLatestPriceVoList.add(goodLatestPriceVo);
                }

            }else{
                throw new NetPlusException(resp.getMessage());
            }
        }

        return goodLatestPriceVoList;
    }


    public String sendErpOrder (String orderNo) {

        Map<String, Object> getOrderInfoMap = new HashMap();
        getOrderInfoMap.put("orderNo", orderNo);

        Tbmqq440Vo tbmqq440Vo = tbmqq440Mapper.getOrderInfo(getOrderInfoMap);
        List<Tbmqq441Vo> tbmqq441VoList = tbmqq441Mapper.getOrderDetail(getOrderInfoMap);
        Approve approve = approveMapper.selectByPrimaryKey(tbmqq440Vo.getApprovecode());


        JK0011Request jk0011Request = new JK0011Request();
        jk0011Request.setOwnercode(tbmqq440Vo.getUserno());
        jk0011Request.setOwnername(tbmqq440Vo.getUsername());
        jk0011Request.setGypbm_pk(tbmqq440Vo.getPonopk());
        jk0011Request.setHth(tbmqq440Vo.getPono());
        jk0011Request.setOrderno(tbmqq440Vo.getOrderno());
        jk0011Request.setDb(tbmqq440Vo.getBuyerno());
        jk0011Request.setSprcodeone(approve.getSprcodeone());
        jk0011Request.setSprnameone(approve.getSprnameone());

        Date createTime = DateUtil.valueOf(tbmqq440Vo.getCreatedate()+tbmqq440Vo.getCreatetime(), "yyyyMMddHHmmss");
        jk0011Request.setXdsj(DateUtil.format(createTime, "yyyy-MM-dd HH:mm:ss"));

        if (!StringUtils.isEmpty(approve.getApprovedate())) {

            Date approveDateTime = DateUtil.valueOf(approve.getApprovedate()+approve.getApprovetime(), "yyyyMMddHHmmss");
            jk0011Request.setSpjssj(DateUtil.format(approveDateTime, "yyyy-MM-dd HH:mm:ss"));

        }else{
            jk0011Request.setSpjssj("");
        }


        if (StringUtils.isEmpty(approve.getSprcodeone())) {
            jk0011Request.setSprcodeone("");
            jk0011Request.setSprnameone("");
        }else{
            jk0011Request.setSprcodeone(approve.getSprcodeone());
            jk0011Request.setSprnameone(approve.getSprnameone());
        }

        if (StringUtils.isEmpty(approve.getSprcodetwo())) {
            jk0011Request.setSprcodetwo("");
            jk0011Request.setSprnametwo("");
        }else{
            jk0011Request.setSprcodetwo(approve.getSprcodetwo());
            jk0011Request.setSprnametwo(approve.getSprnametwo());
        }

        jk0011Request.setScddbm_pk(tbmqq440Vo.getApprovecode());

        if(StringUtils.isEmpty(tbmqq440Vo.getCostdeptpk())){
            jk0011Request.setBmbm_pk(tbmqq440Vo.getDeptno());
            jk0011Request.setBmbh(tbmqq440Vo.getDeptnum());
            jk0011Request.setMc(tbmqq440Vo.getDeptname());
        }else{
            jk0011Request.setBmbm_pk(tbmqq440Vo.getCostdeptpk());
            jk0011Request.setBmbh(tbmqq440Vo.getCostdeptnum());
            jk0011Request.setMc(tbmqq440Vo.getCostdeptname());
        }

        List<JK0011SubRequest> jk0011SubRequestList = tbmqq441VoList.stream().map( t -> {

            JK0011SubRequest jk0011SubRequest = new JK0011SubRequest();
            jk0011SubRequest.setScddmxbm_pk(t.getOrderdetailid());
            jk0011SubRequest.setWzmcbm_pk(t.getMatrlid());
            jk0011SubRequest.setWztm(t.getMatrltm());
            jk0011SubRequest.setWzmc(t.getProductname());
            jk0011SubRequest.setWzbm(t.getMatrlno());
            jk0011SubRequest.setGgxh(t.getSpec());
            jk0011SubRequest.setJldw(t.getQtyunit());
            jk0011SubRequest.setWzcgr(t.getAgent());
            jk0011SubRequest.setJhsl(t.getQty());
            jk0011SubRequest.setHsdj(t.getPrice());
            jk0011SubRequest.setSl(t.getTax());
            jk0011SubRequest.setXmmc(t.getProjectname()+t.getProjectno());
            jk0011SubRequest.setXmbh(t.getProjectno());
            jk0011SubRequest.setXzsbsqdpk(StringUtils.isEmpty(t.getDeviceapplypk()) ? "": t.getDeviceapplypk());
            jk0011SubRequest.setXzsbsqdbh(StringUtils.isEmpty(t.getDeviceapplyno()) ? "": t.getDeviceapplyno());
            jk0011SubRequest.setDhrq(DateUtil.format(t.getLeaddate(), "yyyyMMdd", "yyyy-MM-dd"));
            jk0011SubRequest.setSfsystz(YesNoEnum.Yes.getValue().equals(t.getIsneedpic()) ? "1":"0");
            jk0011SubRequest.setJhlybj("1");
            jk0011SubRequest.setDdshdd(tbmqq440Vo.getConsigneeaddr());
            jk0011SubRequest.setSfzkbj(OneOrZeroEnum.One.getValue().equals(tbmqq440Vo.getConsigneeaddrtype()) ? "0": "1");
            jk0011SubRequest.setBmtxbm_pk(t.getLinepk());
            jk0011SubRequest.setBmtxmc(t.getLinename());
            jk0011SubRequest.setShrlxfs(StringUtils.isEmpty(tbmqq440Vo.getConsigneephone()) ? "": tbmqq440Vo.getConsigneephone());
            jk0011SubRequest.setShrmc(StringUtils.isEmpty(tbmqq440Vo.getConsigneename()) ? "": tbmqq440Vo.getConsigneename());
            jk0011SubRequest.setShrgh(StringUtils.isEmpty(tbmqq440Vo.getConsigneeno()) ? "":  tbmqq440Vo.getConsigneeno());
            jk0011SubRequest.setBz(StringUtils.isEmpty(t.getRemark2()) ? "": t.getRemark2());
            jk0011SubRequest.setGzsbjh(StringUtils.isEmpty(t.getDevicesimpleno()) ? "" : t.getDevicesimpleno());

            return jk0011SubRequest;
        }).collect(Collectors.toList());

        jk0011Request.setDetail(jk0011SubRequestList);
        BaseRequest<JK0011Request> jk0011RequestBaseRequest = new BaseRequest();
        jk0011RequestBaseRequest.setReqId(UuidUtil.getUuid());
        jk0011RequestBaseRequest.setReqTime(String.valueOf(new Date().getTime()));
        jk0011RequestBaseRequest.setReqData(jk0011Request);

        BaseResponse<JK0011Response> resp = erpOutRest.JK0011(jk0011RequestBaseRequest);

        if (!OneOrZeroEnum.One.getValue().equals(resp.getStatus())) {
            throw new NetPlusException(resp.getMessage());
        }


        return resp.getRespData().getContractNo();
    }

    public String sendZkhOrder (String orderNo) {

        Map<String, Object> getOrderInfoMap = new HashMap();
        getOrderInfoMap.put("orderNo", orderNo);

        Tbmqq440Vo tbmqq440Vo = tbmqq440Mapper.getOrderInfo(getOrderInfoMap);
        List<Tbmqq441Vo> tbmqq441VoList = tbmqq441Mapper.getOrderDetail(getOrderInfoMap);

        SubmitOrderRequest submitOrderRequest = new SubmitOrderRequest();
        submitOrderRequest.setThirdOrder(orderNo);

        //商品信息
        List<Sku_out> skuOutList = tbmqq441VoList.stream().map(t -> {

            Sku_out sku = new Sku_out();
            sku.setSkuId(t.getGoodno());
            sku.setNum(t.getQty().intValue());
            sku.setPrice(t.getPrice());
            sku.setNakedPrice(t.getNotaxprice());
            sku.setThirdSkuId(t.getMatrlno());

            return sku;
        }).collect(Collectors.toList());

        submitOrderRequest.setSku(skuOutList);

        //发票信息
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setType(2);
        invoiceInfo.setContent("永钢易购订单");
        invoiceInfo.setTel(tbmqq440Vo.getSupplierphone());
        invoiceInfo.setTitle(tbmqq440Vo.getInvoicetitle());
        invoiceInfo.setEnterpriseTaxpayer(tbmqq440Vo.getSuppliertaxno());
        invoiceInfo.setBank(tbmqq440Vo.getSupplierbankname());
        invoiceInfo.setAccount(tbmqq440Vo.getSuppliercardno());
        invoiceInfo.setAddress(tbmqq440Vo.getSupplieraddr());
        submitOrderRequest.setInvoiceInfo(invoiceInfo);

        //收货人
        if (tbmqq440Vo.getConsigneeaddrtype().equals(AddrTypeEnum.ZK.getCode())) {

            Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_ORDER_ZKH_ZK_CONSIGNEE.code);
            List<String> consigneeInfo = new Gson().fromJson(tbmqq461.getVal(), ArrayList.class);

            submitOrderRequest.setName(consigneeInfo.get(1));
            submitOrderRequest.setMobile(consigneeInfo.get(0));
        }else{
            submitOrderRequest.setName(tbmqq440Vo.getConsigneename());
            submitOrderRequest.setMobile(tbmqq440Vo.getConsigneephone());
        }

        submitOrderRequest.setAddress(tbmqq440Vo.getConsigneefulladdr());

        submitOrderRequest.setAutoConfirm(true);
        submitOrderRequest.setInvoiceName(tbmqq440Vo.getTickreceivername());
        submitOrderRequest.setInvoicePhone(tbmqq440Vo.getTickreceiverphone());
        submitOrderRequest.setInvoiceAddress(tbmqq440Vo.getTickreceiveraddr());

        SubmitOrderVo result = zkhOutRest.submitOrder(submitOrderRequest);
        return result.getOrderId();
    }



    public List<JK0004SubResponse> getLineList () {

        JK0004Request jk0004Request = new JK0004Request();
        BaseRequest<JK0004Request> baseRequest = new BaseRequest();
        baseRequest.setReqId(UuidUtil.getUuid());
        baseRequest.setReqTime(String.valueOf(new Date().getTime()));
        baseRequest.setReqData(jk0004Request);

        BaseResponse<JK0004Response> resp = erpOutRest.JK0004(baseRequest);

        if (OneOrZeroEnum.One.getValue().equals(resp.getStatus())) {

            return resp.getRespData().getData();

        }else{

            throw new NetPlusException(resp.getMessage());
        }
    }


    public PageBean<JK0005SubResponse> getProject(GetProjectRequest getProjectRequest){

        JK0005Request jk0005Request = new JK0005Request();
        jk0005Request.setXmmc(getProjectRequest.getSearchKey());
        jk0005Request.setPageIndex(getProjectRequest.getPageIndex());
        jk0005Request.setPageSize(getProjectRequest.getPageSize());

        BaseRequest<JK0005Request> q = new BaseRequest();
        q.setReqData(jk0005Request);
        q.setReqId(UuidUtil.getUuid());
        q.setReqTime(String.valueOf(new Date().getTime()));

        BaseResponse<JK0005Response> resp = erpOutRest.JK0005(q);

        if (OneOrZeroEnum.One.getValue().equals(resp.getStatus())) {

            PageBean<JK0005SubResponse> pageBean = new PageBean();
            pageBean.setItems(resp.getRespData().getRows());
            pageBean.setTotalCount(resp.getRespData().getTotal());

            return pageBean;
        }else{
            throw new NetPlusException(resp.getMessage());
        }
    }

    public PageBean<JK0006SubResponse> getDevice(GetDeviceRequest getDeviceRequest){

        JK0006Request jk0006Request = new JK0006Request();
        jk0006Request.setXzsbsqdbh(StringUtils.isEmpty(getDeviceRequest.getSearchKey()) ? "":getDeviceRequest.getSearchKey());


        BaseRequest<JK0006Request> q = new BaseRequest();
        q.setReqData(jk0006Request);
        q.setReqId(UuidUtil.getUuid());
        q.setReqTime(String.valueOf(new Date().getTime()));
        BaseResponse<JK0006Response> resp = erpOutRest.JK0006(q);

        if (OneOrZeroEnum.One.getValue().equals(resp.getStatus())) {

            PageBean<JK0006SubResponse> pageBean = new PageBean();
            pageBean.setItems(resp.getRespData().getData());
            return pageBean;
        }else{
            throw new NetPlusException(resp.getMessage());
        }

    }

    public void commitOa (Approve approve, String userNo) {

        //1.获取条线对应的审批人, 校验审批人
//        JK0016Request jk0016Request = new JK0016Request();
//        jk0016Request.setBmbm_pk(tbmqq440Vo.getDeptno());
//        jk0016Request.setBmtxbm_pk(tbmqq440Vo.getLinepk());
//        BaseRequest<JK0016Request> jk0016RequestBaseRequest = new BaseRequest();
//        jk0016RequestBaseRequest.setReqId(UuidUtil.getUuid());
//        jk0016RequestBaseRequest.setReqTime(String.valueOf(new Date().getTime()));
//        jk0016RequestBaseRequest.setReqData(jk0016Request);
//        BaseResponse<JK0016Response> jk0016BaseResponse = erpOutRest.JK0016(jk0016RequestBaseRequest);
//
//        if (OneOrZeroEnum.Zero.getValue().equals(jk0016BaseResponse.getStatus())) {
//            throw new NetPlusException(jk0016BaseResponse.getMessage());
//        }
//
//        if (ObjectUtil.isEmpty(jk0016BaseResponse.getRespData().getData())) {
//            throw new NetPlusException("部门条线审批人接口查询无数据");
//        }
//
//        JK0016SubResponse jk0016SubResponse = jk0016BaseResponse.getRespData().getData().get(0);
//
//        List<JK0016SprOneResponse> jk0016SprOneResponseList = jk0016SubResponse.getSprone();
//        List<JK0016SprTwoResponse> jk0016SprTwoResponseList = jk0016SubResponse.getSprtwo();
//        String sprNameOne = "";
//        String sprNameTwo = "";
//
//        Map<String, JK0016SprOneResponse> jk0016SprOneResponseMap = jk0016SprOneResponseList
//                .stream()
//                .collect(Collectors.toMap(JK0016SprOneResponse::getSprcodeone, j -> j));
//
//
//        if (!jk0016SprOneResponseMap.keySet().contains(sprCodeOne)) {
//            throw new NetPlusException(String.format("审批流中，一级审批人：%s不存在", sprCodeOne));
//        }else{
//            sprNameOne = jk0016SprOneResponseMap.get(sprCodeOne).getSprnameone();
//        }
//
//
//        if (ObjectUtil.nonEmpty(jk0016SprTwoResponseList)) {
//
//            Map<String, JK0016SprTwoResponse> jk0016SprTwoResponseMap = jk0016SprTwoResponseList
//                    .stream()
//                    .collect(Collectors.toMap(JK0016SprTwoResponse::getSprcodetwo, j -> j));
//
//
//            if (!jk0016SprTwoResponseMap.keySet().contains(sprCodeTwo)) {
//                throw new NetPlusException(String.format("审批流中，二级审批人：%s不存在", sprCodeTwo));
//            }else {
//                sprNameTwo = jk0016SprTwoResponseMap.get(sprCodeTwo).getSprnametwo();
//            }
//        }

        //2.获取审批流程节点信息
        BaseRequest<JK0017Request> jk0017Request = new BaseRequest();
        jk0017Request.setReqId(UuidUtil.getUuid());
        jk0017Request.setReqTime(String.valueOf(new Date().getTime()));
        BaseResponse<JK0017Response> jk0017BaseResponse = erpOutRest.JK0017(jk0017Request);
        logger.info(String.format("查询审批流程节点信息接口：%s", new Gson().toJson(jk0017BaseResponse)));


        if (OneOrZeroEnum.Zero.getValue().equals(jk0017BaseResponse.getStatus())) {
            throw new NetPlusException(jk0017BaseResponse.getMessage());
        }
        JK0017SubResponse jk0017SubResponse = jk0017BaseResponse.getRespData().getResult();
        List<NodeList> nodeListList = jk0017SubResponse.getNodeList();
        List<WorkFlowList> workFlowListList = jk0017SubResponse.getWorkFlowList();


        //3.保存审批流信息
        JK0018Request jk0018Request = new JK0018Request();
        jk0018Request.setBusinessId(approve.getApprovecode());
        jk0018Request.setCreaterCode(userNo);
        List<JK0018SubRequest> jk0018SubRequestList = new ArrayList();

        //一级审批人
        JK0018SubRequest jk0018SubRequest1 = new JK0018SubRequest();
        jk0018SubRequest1.setLineId(nodeListList.get(0).getLineId());
        jk0018SubRequest1.setNodeIndex(nodeListList.get(0).getNodeIndex());
        jk0018SubRequest1.setNodeName(nodeListList.get(0).getNodeName());
        jk0018SubRequest1.setDefaultApproveCode(approve.getSprcodeone());
        jk0018SubRequest1.setDefaultApproveName(approve.getSprnameone());
        jk0018SubRequestList.add(jk0018SubRequest1);


        //二级审批人
        if (!StringUtils.isEmpty(approve.getSprcodetwo())) {
            JK0018SubRequest jk0018SubRequest2 = new JK0018SubRequest();
            jk0018SubRequest2.setLineId(nodeListList.get(1).getLineId());
            jk0018SubRequest2.setNodeIndex(nodeListList.get(1).getNodeIndex());
            jk0018SubRequest2.setNodeName(nodeListList.get(1).getNodeName());
            jk0018SubRequest2.setDefaultApproveCode(approve.getSprcodetwo());
            jk0018SubRequest2.setDefaultApproveName(approve.getSprnametwo());
            jk0018SubRequestList.add(jk0018SubRequest2);
        }

        jk0018Request.setNodeList(jk0018SubRequestList);

        BaseRequest<JK0018Request> jk0018RequestBaseRequest = new BaseRequest();
        jk0018RequestBaseRequest.setReqId(UuidUtil.getUuid());
        jk0018RequestBaseRequest.setReqTime(String.valueOf(new Date().getTime()));
        jk0018RequestBaseRequest.setReqData(jk0018Request);
        BaseResponse<JK0018Response> jk0018BaseResponse = erpOutRest.JK0018(jk0018RequestBaseRequest);
        logger.info(String.format("保存申请接口返回结果：%s", new Gson().toJson(jk0018BaseResponse)));

        if (OneOrZeroEnum.Zero.getValue().equals(jk0018BaseResponse.getStatus())) {
            throw new NetPlusException(jk0018BaseResponse.getMessage());
        }

        // 4.提交审批流

        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_HOST.code);
        JK0019Request jk0019Request = new JK0019Request();
        jk0019Request.setBusinessId(approve.getApprovecode());
        jk0019Request.setBusinessNo("");
        jk0019Request.setBusinessQueryUrl(String.format("https://%s/api/mall/getApproveDetail?businessId=", tbmqq461.getVal()));
        jk0019Request.setBusinessUpdateUrl(String.format("https://%s/api/mall/afterApproval/?businessId=", tbmqq461.getVal()));
        jk0019Request.setApprovalCategory("1");
        jk0019Request.setApprovalTitle("永钢商城审批流");
        jk0019Request.setAbstracts("");
        jk0019Request.setCreatorCode(userNo);
        jk0019Request.setWorkFlowType(workFlowListList.get(0).getWorkFlowType());

        BaseRequest<JK0019Request> jk0019RequestBaseRequest = new BaseRequest();
        jk0019RequestBaseRequest.setReqId(UuidUtil.getUuid());
        jk0019RequestBaseRequest.setReqTime(String.valueOf(new Date().getTime()));
        jk0019RequestBaseRequest.setReqData(jk0019Request);
        BaseResponse<JK0019Response> jk0019BaseResponse = erpOutRest.JK0019(jk0019RequestBaseRequest);
        logger.info(String.format("提交流程接口：%s", new Gson().toJson(jk0019BaseResponse)));

        if (OneOrZeroEnum.Zero.getValue().equals(jk0019BaseResponse.getStatus())) {
            throw new NetPlusException(jk0019BaseResponse.getMessage());
        }
    }

    public Map<String, JK0010SubResponse> getQtyCheckResult (List<GoodsVo> goodsVoList) {

        //审批中和未接单的商品数量
        List<Tbmqq441Vo> noCheckMatrlIdQtyList = tbmqq441Mapper.getNoCheckMatrlIdQty();
        Map<String, BigDecimal> noCheckMatrlIdQty = noCheckMatrlIdQtyList
                .stream()
                .collect(Collectors.toMap(Tbmqq441Vo::getMatrlid, t -> t.getQty()));

        JK0010Request jk0010Request = new JK0010Request();
        List<JK0010SubRequest> jk0010SubRequestList = goodsVoList
                .stream()
                .map( g -> {

                    //检验库存数量=审批中数量+未接单数量+当前购买数量
                    BigDecimal totalQty = g.getCartQty();
                    if (noCheckMatrlIdQty.keySet().contains(g.getMatrlid())) {
                        totalQty = NumberUtil.add2Weight(totalQty, noCheckMatrlIdQty.get(g.getMatrlid()));
                    }

                    JK0010SubRequest jk0010SubRequest = new JK0010SubRequest();
                    jk0010SubRequest.setJhsl(totalQty);
                    jk0010SubRequest.setWzmcbm_pk(g.getMatrlid());
                    return jk0010SubRequest;

                }).collect(Collectors.toList());

        jk0010Request.setData(jk0010SubRequestList);

        BaseRequest<JK0010Request> jk0010BaseRequest = new BaseRequest();
        jk0010BaseRequest.setReqId(UuidUtil.getUuid());
        jk0010BaseRequest.setReqTime(String.valueOf(new Date().getTime()));
        jk0010BaseRequest.setReqData(jk0010Request);

        BaseResponse<JK0010Response> jk0010BaseResponse = erpOutRest.JK0010(jk0010BaseRequest);
        logger.info(String.format("查询库存接口：%s", new Gson().toJson(jk0010BaseResponse)));

        if (OneOrZeroEnum.Zero.getValue().equals(jk0010BaseResponse.getStatus())) {
            throw new NetPlusException(jk0010BaseResponse.getMessage());
        }

        List<JK0010SubResponse> jk0010SubResponseList = jk0010BaseResponse.getRespData().getData();;

        if (ObjectUtil.isEmpty(jk0010SubRequestList)) {
            throw new NetPlusException("查询仓库库存接口数据为空");
        }


        jk0010SubResponseList.forEach( j -> {

            j.setRemainQty(NumberUtil.sub2Weight(
                    NumberUtil.sub2Weight(j.getKcslsx(), j.getKcsl()),
                    noCheckMatrlIdQty.get(j.getWzmcbm_pk())
            ));

        });


        Map<String, JK0010SubResponse> jk0010SubResponseMap = jk0010SubResponseList
                .stream()
                .collect(Collectors.toMap(JK0010SubResponse::getWzmcbm_pk, j -> j));

        return jk0010SubResponseMap;
    }

    public JK0009SubResponse getBudgetCheckResult (String deptNo, String linePk, BigDecimal totalAmt) {

        JK0009Request jk0009Request = new JK0009Request();
        jk0009Request.setBmbm_pk(deptNo);
        jk0009Request.setBmtxbm_pk(linePk);
        jk0009Request.setZje(totalAmt);

        BaseRequest<JK0009Request> jk0009BaseRequest = new BaseRequest();
        jk0009BaseRequest.setReqId(UuidUtil.getUuid());
        jk0009BaseRequest.setReqTime(String.valueOf(new Date().getTime()));
        jk0009BaseRequest.setReqData(jk0009Request);
        BaseResponse<JK0009Response> jk0009BaseResponse = erpOutRest.JK0009(jk0009BaseRequest);

        if (OneOrZeroEnum.Zero.getValue().equals(jk0009BaseResponse.getStatus())) {
            throw new NetPlusException(jk0009BaseResponse.getMessage());
        }


        if (ObjectUtil.isEmpty(jk0009BaseResponse.getRespData().getData())) {

            JK0009SubResponse jk0009SubResponse = new JK0009SubResponse();
            jk0009SubResponse.setNum("1");
            jk0009SubResponse.setJhsyje(null);

            return jk0009SubResponse;
        }

        return jk0009BaseResponse.getRespData().getData().get(0);
    }


    public BaseResponse<JK0022Response> viewApproveProgress(ViewApproveProgressRequest request) {
        String reqId = UuidUtil.getUuid();
        BaseRequest<JK0022Request> baseRequest=new BaseRequest<>();
        baseRequest.setReqId(reqId);
        baseRequest.setReqTime(String.valueOf(System.currentTimeMillis()));

        JK0022Request jk0022Request = new JK0022Request();
        jk0022Request.setBusinessId(request.getApproveCode());
        baseRequest.setReqData(jk0022Request);
        BaseResponse<JK0022Response> response = erpOutRest.JK0022(baseRequest);

        if (OneOrZeroEnum.Zero.getValue().equals(response.getStatus())) {
            throw new NetPlusException(response.getMessage());
        }

        return response;
    }


    public List<JK0012SubResponse> getOrderDetailSchedule(List<String> orderDetailIdList) {
        String reqId = UuidUtil.getUuid();
        BaseRequest<JK0012Request> baseRequest=new BaseRequest<>();
        baseRequest.setReqId(reqId);
        baseRequest.setReqTime(String.valueOf(System.currentTimeMillis()));

        JK0012Request jk0012Request = new JK0012Request();
        jk0012Request.setScddmxbm_pk_list(orderDetailIdList);
        baseRequest.setReqData(jk0012Request);
        BaseResponse<JK0012Response> response = erpOutRest.JK0012(baseRequest);

        if (OneOrZeroEnum.Zero.getValue().equals(response.getStatus())) {
            throw new NetPlusException(response.getMessage());
        }

        return response.getRespData().getData();
    }


    public JK0016SubResponse getSprInfo  (GetSprInfoRequest request) {

        //1.获取条线对应的审批人
        JK0016Request jk0016Request = new JK0016Request();
        jk0016Request.setBmbm_pk(request.getDeptNo());
        jk0016Request.setBmtxbm_pk(request.getLineNo());
        BaseRequest<JK0016Request> jk0016RequestBaseRequest = new BaseRequest();
        jk0016RequestBaseRequest.setReqId(UuidUtil.getUuid());
        jk0016RequestBaseRequest.setReqTime(String.valueOf(new Date().getTime()));
        jk0016RequestBaseRequest.setReqData(jk0016Request);
        BaseResponse<JK0016Response> jk0016BaseResponse = erpOutRest.JK0016(jk0016RequestBaseRequest);
        logger.info(String.format("查询部门条线审批人接口：%s", new Gson().toJson(jk0016BaseResponse)));

        if (OneOrZeroEnum.Zero.getValue().equals(jk0016BaseResponse.getStatus())) {
            throw new NetPlusException(jk0016BaseResponse.getMessage());
        }

        if (ObjectUtil.isEmpty(jk0016BaseResponse.getRespData().getData())) {
            throw new NetPlusException("部门条线审批人接口查询无数据");
        }

        JK0016SubResponse jk0016SubResponse = jk0016BaseResponse.getRespData().getData().get(0);
        return jk0016SubResponse;

    }

    public void addSprName (String deptNo, String linePk, Approve approve) {

        //1.获取条线对应的审批人, 校验审批人
        JK0016Request jk0016Request = new JK0016Request();
        jk0016Request.setBmbm_pk(deptNo);
        jk0016Request.setBmtxbm_pk(linePk);
        BaseRequest<JK0016Request> jk0016RequestBaseRequest = new BaseRequest();
        jk0016RequestBaseRequest.setReqId(UuidUtil.getUuid());
        jk0016RequestBaseRequest.setReqTime(String.valueOf(new Date().getTime()));
        jk0016RequestBaseRequest.setReqData(jk0016Request);
        BaseResponse<JK0016Response> jk0016BaseResponse = erpOutRest.JK0016(jk0016RequestBaseRequest);

        if (OneOrZeroEnum.Zero.getValue().equals(jk0016BaseResponse.getStatus())) {
            throw new NetPlusException(jk0016BaseResponse.getMessage());
        }

        if (ObjectUtil.isEmpty(jk0016BaseResponse.getRespData().getData())) {
            throw new NetPlusException("部门条线审批人接口查询无数据");
        }

        JK0016SubResponse jk0016SubResponse = jk0016BaseResponse.getRespData().getData().get(0);

        List<JK0016SprOneResponse> jk0016SprOneResponseList = jk0016SubResponse.getSprone();
        List<JK0016SprTwoResponse> jk0016SprTwoResponseList = jk0016SubResponse.getSprtwo();
        String sprNameOne = "";
        String sprNameTwo = "";

        Map<String, JK0016SprOneResponse> jk0016SprOneResponseMap = jk0016SprOneResponseList
                .stream()
                .collect(Collectors.toMap(JK0016SprOneResponse::getSprcodeone, j -> j));


        if (!jk0016SprOneResponseMap.keySet().contains(approve.getSprcodeone())) {
            throw new NetPlusException(String.format("审批流中，一级审批人：%s不存在", approve.getSprcodeone()));
        }else{
            sprNameOne = jk0016SprOneResponseMap.get(approve.getSprcodeone()).getSprnameone();
        }


        if (ObjectUtil.nonEmpty(jk0016SprTwoResponseList)) {

            Map<String, JK0016SprTwoResponse> jk0016SprTwoResponseMap = jk0016SprTwoResponseList
                    .stream()
                    .collect(Collectors.toMap(JK0016SprTwoResponse::getSprcodetwo, j -> j));


            if (!jk0016SprTwoResponseMap.keySet().contains(approve.getSprcodetwo())) {
                throw new NetPlusException(String.format("审批流中，二级审批人：%s不存在", approve.getSprcodetwo()));
            }else {
                sprNameTwo = jk0016SprTwoResponseMap.get(approve.getSprcodetwo()).getSprnametwo();
            }
        }

        approve.setSprnameone(sprNameOne);
        approve.setSprnametwo(sprNameTwo);
    }


    public void sendOnOrOffLineStatus (List<GoodsVo> goodsVoList, String isOnOrOffLine) {
        BaseRequest<JK0032Request> jk0032RequestBaseRequest = new BaseRequest();
        JK0032Request jk0032Request = new JK0032Request();
        List<JK0032SubRequest> jk0032SubRequestList = goodsVoList.stream().map( g -> {
            JK0032SubRequest jk0032SubRequest = new JK0032SubRequest();
            jk0032SubRequest.setWztm(g.getMatrltm());
            jk0032SubRequest.setScwzzt(isOnOrOffLine.equals(YesNoEnum.Yes.getValue()) ? 1:0);
            return jk0032SubRequest;
        }).collect(Collectors.toList());


        jk0032Request.setData(jk0032SubRequestList);

        jk0032RequestBaseRequest.setReqId(UuidUtil.getUuid());
        jk0032RequestBaseRequest.setReqTime(String.valueOf(new Date().getTime()));
        jk0032RequestBaseRequest.setReqData(jk0032Request);

        BaseResponse<JK0032Response> jk0032ResponseBaseResponse = erpOutRest.JK0032(jk0032RequestBaseRequest);

        if (OneOrZeroEnum.Zero.getValue().equals(jk0032ResponseBaseResponse.getStatus())) {
            throw new NetPlusException(jk0032ResponseBaseResponse.getMessage());
        }

    }

}
