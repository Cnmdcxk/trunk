package netplus.mall.service.biz;

import com.google.gson.Gson;
import netplus.component.entity.data.KeyValue;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.enums.JK0013Enum;
import netplus.component.entity.enums.MsgTypeEnum;
import netplus.component.entity.enums.OneOrZeroEnum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.component.entity.request.RequestBase;
import netplus.excel.api.request.GenExcelRequest;
import netplus.excel.api.rest.ExportRest;
import netplus.joint.erp.api.request.in.JK0013Request;
import netplus.joint.erp.api.response.out.JK0012.JK0012SubResponse;
import netplus.joint.erp.api.rest.ErpOutRest;
import netplus.mall.api.enums.OrderDetailStatusEnum;
import netplus.mall.api.enums.OrderStatusEnum;
import netplus.mall.api.pojo.ygmalluser.*;
import netplus.mall.api.request.goodsInventory.MallGoodsInventoryPageRequest;
import netplus.mall.api.request.order.*;
import netplus.mall.api.vo.order.*;
import netplus.mall.service.dao.*;
import netplus.messaging.api.request.SendMsgRequest;
import netplus.messaging.api.rest.MessagingRest;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.request.GetSupplierNoRequest;
import netplus.provider.api.request.GetUserInfoRequest;
import netplus.provider.api.rest.CommonRest;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.rest.ProvideRest;
import netplus.provider.api.rest.ServiceConfigRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.utils.date.DateUtil;
import netplus.utils.date.NowDate;
import netplus.utils.number.NumberUtil;
import netplus.utils.object.ObjectUtil;
import netplus.utils.uuid.UuidUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderBiz {

    protected Log logger = LogFactory.getLog(OrderBiz.class);

    @Autowired
    Tbmqq440Mapper tbmqq440Mapper;

    @Autowired
    Tbmqq441Mapper tbmqq441Mapper;

    @Autowired
    IdentityRest identityRest;

    @Autowired
    CommonRest commonRest;

    @Autowired
    ExportRest exportRest;

    @Autowired
    IfaceBiz ifaceBiz;

    @Autowired
    Tbmqq438Mapper tbmqq438Mapper;

    @Autowired
    Tbmqq443Mapper tbmqq443Mapper;

    @Autowired
    MessagingRest messagingRest;

    @Autowired
    ErpOutRest erpOutRest;

    @Autowired
    ServiceConfigRest serviceConfigRest;

    @Autowired
    ApproveMapper approveMapper;

    @Autowired
    ProvideRest provideRest;

    public PageBean<Tbmqq440Vo> getOrderList (GetOrderListRequest request) {

        Map<String, Object> mapParam = getQueryParams(request);
        PageBean<Tbmqq440Vo> pageBean = new PageBean();

        List<Tbmqq440Vo> vos = tbmqq440Mapper.getOrderList(mapParam);

        int count = tbmqq440Mapper.getOrderCount(mapParam);

        vos.forEach(v -> {
            v.setStatusName(OrderStatusEnum.getName(v.getStatus()));
        });

        if("all".equals(request.getPage())){
            List<KeyValue> isTimeOutOrderKV = tbmqq440Mapper.getIsTimeOutOrderKV(mapParam);
            isTimeOutOrderKV.forEach(keyValue ->{
                if ("Y".equals(keyValue.getKey())) {
                    keyValue.setValue("是");
                } else {
                    keyValue.setValue("否");
                }
            });
            pageBean.addResultMap("isTimeOutOrder", isTimeOutOrderKV);
        }

        if("S".equals(request.getPage()) || "all".equals(request.getPage())){
            List<KeyValue> deptNoKV = tbmqq440Mapper.getDeptKV(mapParam);
            List<KeyValue> buyerKV = tbmqq440Mapper.getBuyerKV(mapParam);
            pageBean.addResultMap("deptNoList", deptNoKV);
            pageBean.addResultMap("buyerNoList", buyerKV);
        }

        if("B".equals(request.getPage()) || "all".equals(request.getPage())){
            List<KeyValue> supplierKV = tbmqq440Mapper.getSupplierKV(mapParam);
            List<KeyValue> lineKV = tbmqq440Mapper.getLineKV(mapParam);
            pageBean.addResultMap("supplierList", supplierKV);
            pageBean.addResultMap("lineList", lineKV);
        }

        List<KeyValue> statusKV = tbmqq440Mapper.getStatusKV(mapParam);
        statusKV.forEach(s -> s.setValue(OrderStatusEnum.getName(s.getKey())));

        pageBean.setItems(vos);
        pageBean.setTotalCount(count);
        pageBean.addResultMap("statusList", statusKV);

        return pageBean;
    }

    public PageBean<Tbmqq440Vo> getOrderAndDetailList (GetOrderListRequest request) {
        PageBean<Tbmqq440Vo> pageBean = getOrderList(request);
        List<Tbmqq440Vo> items = pageBean.getItems();
        if(!ObjectUtils.isEmpty(items)){
            List<String> orderNoList = items.stream().map(Tbmqq440Vo::getOrderno).collect(Collectors.toList());
            List<Tbmqq441Vo> orderDetails = tbmqq441Mapper.getOrderDetailByOrderNoList(orderNoList);
            items.forEach(item ->{
                List<Tbmqq441Vo> detailByOrder = orderDetails.stream().filter(detail -> detail.getOrderno().equals(item.getOrderno())).collect(Collectors.toList());
                item.setTbmqq441VoList(detailByOrder);
            });
        }
        return pageBean;
    }

    public int getMyWaitingTakeOrder(){
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("supplierNo",loginUserBean.getCompanyCode());
        mapParam.put("status",OrderStatusEnum.status_15.getCode());
        int count = tbmqq440Mapper.getOrderCount(mapParam);
        return count;
    }

    public Map<String, Object> getTabCount (GetOrderListRequest request) {
        Map<String, Object> mapParam = getMapParam(request);
        return tbmqq440Mapper.getTabCount(mapParam);
    }

    public Tbmqq440Vo getOrderInfo (GetOrderInfoRequest request) {
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        if(loginUserBean.getRole().equals("S")){
            mapParam.put("supplierno",loginUserBean.getCompanyCode());
        }else{
            mapParam.put("userno",loginUserBean.getUserCode());
        }
        Tbmqq440Vo tbmqq440Vo = tbmqq440Mapper.getOrderInfo(mapParam);


        if (ObjectUtil.isEmpty(tbmqq440Vo)) {
            throw new NetPlusException("订单不存在");
        }

        tbmqq440Vo.setStatusName(OrderStatusEnum.getName(tbmqq440Vo.getStatus()));
        tbmqq440Vo.setPayTypeName(tbmqq440Vo.getPaytypename());
        tbmqq440Vo.setPayMethodName(tbmqq440Vo.getPaymethodname());

        GetUserInfoRequest req = new GetUserInfoRequest();
        req.setUserNo(tbmqq440Vo.getCreateuser());
        Tbdu01Vo createUser = identityRest.getUserByUserNo(req);
        if(!ObjectUtils.isEmpty(createUser)){
            tbmqq440Vo.setCreateUserName(createUser.getName());
        }

        List<Tbmqq441Vo> tbmqq441VoList = null;
        if(mapParam.get("pageIndex") != null && mapParam.get("pageSize") != null ){
            tbmqq441VoList = tbmqq441Mapper.getOrderDetailList(mapParam);
            tbmqq440Vo.setTotalCount(tbmqq441Mapper.getOrderDetailCount(mapParam));
        }else{
            tbmqq441VoList = tbmqq441Mapper.getOrderDetail(mapParam);
        }

        if (ObjectUtil.isEmpty(tbmqq441VoList)) {
            throw new NetPlusException("订单明细不存在");
        }

        tbmqq441VoList.forEach(t -> {
            t.setStatusName(OrderDetailStatusEnum.getName(t.getStatus()));
            if(!StringUtils.isEmpty(t.getExpressno())){
                t.setExpressNoList(Arrays.asList(t.getExpressno().split(",")));
            }
        });
        tbmqq440Vo.setTbmqq441VoList(tbmqq441VoList);
        List<Tbmqq443> Tbmqq443List = tbmqq443Mapper.getAppendix(mapParam);
        tbmqq440Vo.setTbmqq443List(Tbmqq443List);

        return tbmqq440Vo;
    }

    public String exportOrder (GetOrderListRequest request) throws Exception {
        Map<String, Object> mapParam = getQueryParams(request);

        List<Tbmqq440Vo> vos = tbmqq440Mapper.getOrderList(mapParam);

        List<PurchaseOrderExportVo> voList = vos.stream().map(item -> {
            PurchaseOrderExportVo vo = new PurchaseOrderExportVo();
            BeanUtils.copyProperties(item,vo);

            vo.setCreateTimeStr(DateUtil.format(vo.getCreatedate()+vo.getCreatetime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
            vo.setStatusName(OrderStatusEnum.getName(vo.getStatus()));

            return vo;
        }).collect(Collectors.toList());

        String tempName = "";
        if ("B".equals(request.getPage()) || "all".equals(request.getPage())) {
            tempName = "tmp_pur_order";
        }else{
            tempName = "tmp_sale_order";
        }

        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(voList);
        genExcelRequest.setTemplateName(tempName);
        genExcelRequest.setSheetName("导出订单");
        String url = exportRest.genExcel(genExcelRequest);

        return url;
    }

    @NotNull
    private Map<String, Object> getQueryParams(GetOrderListRequest request) {
        LoginUserBean userBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = getMapParam(request);
        if("all".equals(request.getPage())){

        }else if("B".equals(request.getPage())){
            List<String> userNoList = new ArrayList<>();
            userNoList.add(userBean.getUserCode());
            mapParam.put("userNoList",userNoList);
        }else{
            Object list = mapParam.get("statusList");
            if(ObjectUtils.isEmpty(list)) {
                List<String> statusList = new ArrayList<>();
                statusList.add(OrderStatusEnum.status_15.getCode());
                statusList.add(OrderStatusEnum.status_25.getCode());
                statusList.add(OrderStatusEnum.status_30.getCode());
                statusList.add(OrderStatusEnum.status_35.getCode());
                mapParam.put("statusList", statusList);
            }
            mapParam.put("supplierNo",userBean.getCompanyCode());
        }
        return mapParam;
    }

    public String exportOrderDetail (GetOrderListRequest request) throws Exception {
        Map<String, Object> mapParam = getQueryParams(request);

        List<PurchaseOrderDetailExportVo> vos = tbmqq440Mapper.getOrderDetaiExportlList(mapParam);
        vos.forEach(item ->{
            String bandAndProductName=item.getProductname();
            if(!StringUtils.isEmpty(item.getBrand())){
                bandAndProductName=bandAndProductName+"/"+item.getBrand();
            }
            item.setBandAndProductName(bandAndProductName);
            item.setTaxStr(item.getTax().multiply(new BigDecimal(100)).toString()+"%");
            item.setIsNeedPicStr("Y".equals(item.getIsneedpic())?"是":"否");
            item.setCreateTimeStr(DateUtil.format(item.getCreatedate()+item.getCreatetime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
            item.setReceivingTimeStr(DateUtil.format(item.getReceivingdate()+item.getReceivingtime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
            if(item.getLeadtimenum() != null){
                item.setLeadTimeStr(item.getLeadtimenum().toString()+"个工作日");
            }
            item.setStatusName(OrderDetailStatusEnum.getName(item.getStatus()));
        });
        String tempName = "";
        String sheetName = "";
        if ("B".equals(request.getPage()) || "all".equals(request.getPage())) {
            tempName = "tmp_pur_order_detail";
            sheetName = "商城采购订单明细导出";
        }else{
            tempName = "tmp_sale_order_detail";
            sheetName = "商城销售订单明细导出";
        }

        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(vos);
        genExcelRequest.setTemplateName(tempName);
        genExcelRequest.setSheetName(sheetName);
        String url = exportRest.genExcel(genExcelRequest);

        return url;

    }


    private <T extends RequestBase> Map<String, Object>  getMapParam (T request) {

        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        return mapParam;

    }

    public int changeOrderStatus (ChangeOrderStatusRequest request) {
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);

        NowDate nowDate = new NowDate();
        mapParam.put("updateDate", nowDate.getDateStr());
        mapParam.put("updateTime", nowDate.getTimeStr());
        mapParam.put("updateUser", request.getOpUser());

        int updateCount = tbmqq440Mapper.changeOrderStatus(mapParam);
        if (updateCount != 1) {
            throw new NetPlusException("订单状态更新失败");
        }

        return updateCount;
    }

    public int changeOrderDetailStatus (ChangeOrderDetailStatusRequest request) {
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        NowDate nowDate = new NowDate();
        mapParam.put("updateDate", nowDate.getDateStr());
        mapParam.put("updateTime", nowDate.getTimeStr());
        mapParam.put("updateUser", request.getOpUser());
        int updateCount = tbmqq441Mapper.changeOrderDetailStatus(mapParam);
        if (updateCount <= 0) {
            throw new NetPlusException("订单明细更新失败");
        }

        return updateCount;
    }


    @Transactional(rollbackFor = Exception.class)
    public void receivingOrder(ReceivingOrderRequest request){

        if (StringUtils.isEmpty(request.getOrderNo())) {
            throw new NetPlusException("订单号不能为空");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        NowDate nowDate = new NowDate();

        Tbmqq440 orderInfo = tbmqq440Mapper.selectByPrimaryKey(request.getOrderNo());
        if (ObjectUtil.isEmpty(orderInfo)) {
            throw new NetPlusException("订单号不存在");
        }

        GetUserInfoRequest req = new GetUserInfoRequest();
        req.setUserNo(orderInfo.getUserno());
        Tbdu01Vo userInfo = identityRest.getUserByUserNo(req);

        // 修改订单状态
        ChangeOrderStatusRequest changeOrderStatusRequest = new ChangeOrderStatusRequest();
        changeOrderStatusRequest.setNewStatus(OrderStatusEnum.status_25.getCode());
        changeOrderStatusRequest.setOldStatus(OrderStatusEnum.status_15.getCode());
        changeOrderStatusRequest.setOrderNo(request.getOrderNo());
        changeOrderStatusRequest.setSupplierNo(loginUserBean.getCompanyCode());
        changeOrderStatusRequest.setOpUser(loginUserBean.getUserCode());
        changeOrderStatus(changeOrderStatusRequest);

        int tbmqq440UpdateCount = tbmqq440Mapper.updateReceivingDate(request.getOrderNo(), nowDate.getDateStr(), nowDate.getTimeStr());
        if (tbmqq440UpdateCount != 1) {
            throw new NetPlusException("更新接单时间失败");
        }

        // 修改订单明细状态
        ChangeOrderDetailStatusRequest changeOrderDetailStatusRequest = new ChangeOrderDetailStatusRequest();
        changeOrderDetailStatusRequest.setOrderNo(request.getOrderNo());
        changeOrderDetailStatusRequest.setNewStatus(OrderDetailStatusEnum.status_25.getCode());
        changeOrderDetailStatusRequest.setOldStatus(OrderDetailStatusEnum.status_15.getCode());
        changeOrderDetailStatusRequest.setSupplierNo(loginUserBean.getCompanyCode());
        changeOrderDetailStatusRequest.setOpUser(loginUserBean.getUserCode());
        changeOrderDetailStatus(changeOrderDetailStatusRequest);

        int tbmqq441UpdateCount = tbmqq441Mapper.updateLeadDate(request.getOrderNo());
        if (tbmqq441UpdateCount <= 0) {
            throw new NetPlusException("更新交货时间失败");
        }

        // 更新销量
        updateSale(request.getOrderNo());

        //下发订单给震坤行系统
        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_OUT_SUPPLIER.code);
        if (tbmqq461.getVal().equals(loginUserBean.getCompanyCode())) {
            String zkhOrder = ifaceBiz.sendZkhOrder(request.getOrderNo());

            Tbmqq440 tbmqq440Two = new Tbmqq440();
            tbmqq440Two.setOrderno(request.getOrderNo());
            tbmqq440Two.setThrplatorderno(zkhOrder);
            int zkhCount = tbmqq440Mapper.updateByPrimaryKeySelective(tbmqq440Two);
            if (zkhCount != 1) {
                throw new NetPlusException("更新震坤行订单号失败");
            }
        }

        // 下发订单给仓库系统
        String ckOrderNo = ifaceBiz.sendErpOrder(request.getOrderNo());

        Tbmqq440 tbmqq440 = new Tbmqq440();
        tbmqq440.setOrderno(request.getOrderNo());
        tbmqq440.setErporderno(ckOrderNo);

        int ckCount = tbmqq440Mapper.updateByPrimaryKeySelective(tbmqq440);
        if (ckCount != 1) {
            throw new NetPlusException("更新仓库订单号失败");
        }

//        //下发订单给震坤行系统
//        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_OUT_SUPPLIER.code);
//        if (tbmqq461.getVal().equals(loginUserBean.getCompanyCode())) {
//            String zkhOrder = ifaceBiz.sendZkhOrder(request.getOrderNo());
//
//            Tbmqq440 tbmqq440Two = new Tbmqq440();
//            tbmqq440Two.setOrderno(request.getOrderNo());
//            tbmqq440Two.setThrplatorderno(zkhOrder);
//            int zkhCount = tbmqq440Mapper.updateByPrimaryKeySelective(tbmqq440Two);
//            if (zkhCount != 1) {
//                throw new NetPlusException("更新震坤行订单号失败");
//            }
//        }

        if (ObjectUtil.nonEmpty(userInfo)) {

            //发送站内消息
            List<SendMsgRequest> sendMsgRequestList = new ArrayList();
            SendMsgRequest sendMsgRequest = new SendMsgRequest();
            sendMsgRequest.setSendUserNo(loginUserBean.getUserCode());
            sendMsgRequest.setReceiveUserNo(orderInfo.getUserno());
            sendMsgRequest.setMsgType(MsgTypeEnum.ORDER_ACCEPT);
            sendMsgRequest.setUrl("/mall/purchaseOrder");
            sendMsgRequest.setParams(new String[]{request.getOrderNo(), loginUserBean.getCompanyName()});
            sendMsgRequestList.add(sendMsgRequest);
            messagingRest.sendMsg(sendMsgRequestList);

            if (!StringUtils.isEmpty(userInfo.getPhone())) {

                try {

                    //发送短信
                    JK0013Enum acceptOrderConfirm = JK0013Enum.ACCEPT_ORDER_CONFIRM;
                    Map<String, String> params = acceptOrderConfirm.getParams();
                    params.put("1",request.getOrderNo());
                    params.put("2",loginUserBean.getCompanyCode());
                    params.put("3",loginUserBean.getCompanyName());

                    String reqId = UuidUtil.getUuid();

                    BaseRequest<JK0013Request> request2 = new BaseRequest<JK0013Request>();
                    request2.setReqId(reqId);
                    request2.setReqTime(String.valueOf(System.currentTimeMillis()));

                    JK0013Request jk0013Request = new JK0013Request();
                    jk0013Request.setReceiver(Arrays.asList(userInfo.getPhone()));
                    jk0013Request.setTemplateId(acceptOrderConfirm.getTemplateId());
                    jk0013Request.setTemplateParas(params);
                    request2.setReqData(jk0013Request);

                    erpOutRest.JK0013(request2);


                }catch (Exception e) {
                    logger.info(String.format("短信发送失败：%s", e.getMessage()));
                }


            }

        }
    }

    private void updateSale (String orderNo) {
        Map<String, Object> getOrderInfoMap = new HashMap();
        getOrderInfoMap.put("orderNo", orderNo);
        NowDate nowDate = new NowDate();

        Tbmqq440Vo tbmqq440Vo = tbmqq440Mapper.getOrderInfo(getOrderInfoMap);
        List<Tbmqq441Vo> tbmqq441VoList = tbmqq441Mapper.getOrderDetail(getOrderInfoMap);

        List<Tbmqq438> tbmqq438List = tbmqq441VoList.stream().map(t -> {

            Tbmqq438 tbmqq438 = new Tbmqq438();
            tbmqq438.setMatrlid(t.getMatrlid());
            tbmqq438.setMatrlno(t.getMatrlno());
            tbmqq438.setMatrltm(t.getMatrltm());
            tbmqq438.setSupplierno(tbmqq440Vo.getSupplierno());
            tbmqq438.setSuppliername(tbmqq440Vo.getSuppliername());
            tbmqq438.setQty(t.getQty());
            tbmqq438.setAmt(t.getAmt());
            tbmqq438.setUpdatedate(nowDate.getDateStr());
            tbmqq438.setUpdatetime(nowDate.getTimeStr());

            return tbmqq438;

        }).collect(Collectors.toList());

        List<String> matrlIdList = tbmqq438Mapper.getMatrlIdBySupplierNo(tbmqq440Vo.getSupplierno());
        List<Tbmqq438> insertList = new ArrayList();
        List<Tbmqq438> updateList = new ArrayList();

        for (Tbmqq438 tbmqq438: tbmqq438List) {

            if (matrlIdList.contains(tbmqq438.getMatrlid())) {
                updateList.add(tbmqq438);
            }else{

                tbmqq438.setCreatetime(nowDate.getTimeStr());
                tbmqq438.setCreatedate(nowDate.getDateStr());

                insertList.add(tbmqq438);
                matrlIdList.add(tbmqq438.getMatrlid());
            }
        }

        if (insertList.size() > 0) {
            tbmqq438Mapper.batchInsert(insertList);
        }

        for (Tbmqq438 tbmqq438: updateList) {
            int updateCount = tbmqq438Mapper.updateSale(tbmqq438);
            if (updateCount != 1) {
                throw new NetPlusException("销量更新失败");
            }
        }
    }

    //同步订单明细进度
    @Transactional(rollbackFor = Exception.class)
    public void syncOrderDetailSchedule(SyncOrderDetailScheduleRequest request) {

        List<Tbmqq441Vo> orderDetailList = tbmqq441Mapper.getSyncScheduleOrderDetailId(ObjectUtil.transBeanToMap(request));
        if (orderDetailList.size() > 0) {

            Map<String, String> orderDetailMap = orderDetailList
                    .stream()
                    .collect(Collectors.toMap(Tbmqq441Vo::getOrderdetailid, o -> o.getOrderno()));

            List<String> orderDetailIdList = new ArrayList(orderDetailMap.keySet());

            NowDate nowDate = new NowDate();

            int pageSize = request.getPageSize();
            int totalPage = orderDetailIdList.size() / pageSize;
            int remain = orderDetailIdList.size() % pageSize;
            if (remain > 0) {
                totalPage  = totalPage + 1;
            }

            for (int i = 1; i <= totalPage; i++) {

                int success = 0;
                int start = (i - 1) * pageSize;
                int end = i * pageSize;

                if (end > orderDetailIdList.size()) {
                    end = orderDetailIdList.size();
                }

                int totalCount = end - start;
                List<String> subOrderDetailIdList = orderDetailIdList.subList(start, end);

                logger.info(String.format("订单明细进度同步第%d页: 共%d个", i, totalCount));
                List<JK0012SubResponse> jk0012ResponseList = ifaceBiz.getOrderDetailSchedule(subOrderDetailIdList);


                if (jk0012ResponseList.size() > 0) {

                    Set<String> updateOrderList = new HashSet();
                    for (JK0012SubResponse jk0012SubResponse: jk0012ResponseList) {
                        Tbmqq441Vo tbmqq441Vo = new Tbmqq441Vo();
                        tbmqq441Vo.setOrderdetailid(jk0012SubResponse.getScddmxbm_pk());
                        tbmqq441Vo.setDeliqty(jk0012SubResponse.getSgsl());
                        tbmqq441Vo.setTakedeliqty(jk0012SubResponse.getRksl());
                        tbmqq441Vo.setExpressno(jk0012SubResponse.getKddh().trim());
                        tbmqq441Vo.setUpdatedate(nowDate.getDateStr());
                        tbmqq441Vo.setUpdatetime(nowDate.getTimeStr());
                        tbmqq441Vo.setUpdateuser(SysCodeEnum.JOB.code);

                        if (OneOrZeroEnum.One.getValue().equals(jk0012SubResponse.getZt())) {
                            tbmqq441Vo.setStatus(OrderDetailStatusEnum.status_35.getCode());
                        }else{

                            if (ObjectUtil.nonEmpty(jk0012SubResponse.getRksl()) && NumberUtil.gt(jk0012SubResponse.getRksl(), BigDecimal.ZERO)) {
                                tbmqq441Vo.setStatus(OrderDetailStatusEnum.status_30.getCode());
                            }
                        }

                        tbmqq441Mapper.updateQtyByOrderDetailId(tbmqq441Vo);
                        updateOrderList.add(orderDetailMap.get(jk0012SubResponse.getScddmxbm_pk()));
                        success ++;
                    }

                    List<Tbmqq440Vo> tbmqq440VoList = tbmqq440Mapper.getOrderScheduleQty(new ArrayList(updateOrderList));
                    for (Tbmqq440Vo tbmqq440Vo: tbmqq440VoList) {


                        tbmqq440Vo.setUpdatedate(nowDate.getDateStr());
                        tbmqq440Vo.setUpdatetime(nowDate.getTimeStr());
                        tbmqq440Vo.setUpdateuser(SysCodeEnum.JOB.code);

                        //这里的状态是订单明细中分组统计后取的最小状态，最小的状态如果是已完成，则说明订单也完成了
                        if (OrderDetailStatusEnum.status_35.getCode().equals(tbmqq440Vo.getStatus())) {
                            tbmqq440Vo.setStatus(OrderStatusEnum.status_30.getCode());
                        }else{
                            tbmqq440Vo.setStatus(null);
                        }

                        tbmqq440Mapper.updateOrderScheduleQty(tbmqq440Vo);
                    }


                    logger.info(String.format("订单明细进度同步第%d页: 共%d个, 成功%d个, 失败%d个",  i, totalCount, success, totalCount - success));

                }else{

                    logger.info(String.format("订单明细进度同步第%d页: 查询不到数据", totalCount));
                }
            }

        }else{
            logger.info(String.format("暂无订单明细进度需要同步"));
        }

    }

    //同步订单明细开票进度
    @Transactional(rollbackFor = Exception.class)
    public void syncInvoiceOrderDetailSchedule(SyncOrderDetailScheduleRequest request) {

        List<Tbmqq441Vo> orderDetailList = tbmqq441Mapper.getSyncInvoiceOrderDetailId(ObjectUtil.transBeanToMap(request));
        if (orderDetailList.size() > 0) {

            Map<String, String> orderDetailMap = orderDetailList
                    .stream()
                    .collect(Collectors.toMap(Tbmqq441Vo::getOrderdetailid, o -> o.getOrderno()));

            List<String> orderDetailIdList = new ArrayList(orderDetailMap.keySet());

            NowDate nowDate = new NowDate();

            int pageSize = request.getPageSize();
            int totalPage = orderDetailIdList.size() / pageSize;
            int remain = orderDetailIdList.size() % pageSize;
            if (remain > 0) {
                totalPage  = totalPage + 1;
            }

            for (int i = 1; i <= totalPage; i++) {

                int success = 0;
                int start = (i - 1) * pageSize;
                int end = i * pageSize;

                if (end > orderDetailIdList.size()) {
                    end = orderDetailIdList.size();
                }

                int totalCount = end - start;
                List<String> subOrderDetailIdList = orderDetailIdList.subList(start, end);

                logger.info(String.format("订单明细开票进度同步第%d页: 共%d个", i, totalCount));
                List<JK0012SubResponse> jk0012ResponseList = ifaceBiz.getOrderDetailSchedule(subOrderDetailIdList);


                if (jk0012ResponseList.size() > 0) {

                    Set<String> updateOrderList = new HashSet();
                    for (JK0012SubResponse jk0012SubResponse: jk0012ResponseList) {

                        Tbmqq441Vo tbmqq441Vo = new Tbmqq441Vo();
                        tbmqq441Vo.setOrderdetailid(jk0012SubResponse.getScddmxbm_pk());
                        tbmqq441Vo.setInvoiceqty(jk0012SubResponse.getJssl());
                        tbmqq441Vo.setUpdatedate(nowDate.getDateStr());
                        tbmqq441Vo.setUpdatetime(nowDate.getTimeStr());
                        tbmqq441Vo.setUpdateuser(SysCodeEnum.JOB.code);
                        tbmqq441Mapper.updateInvoiceQtyByOrderDetailId(tbmqq441Vo);
                        updateOrderList.add(orderDetailMap.get(jk0012SubResponse.getScddmxbm_pk()));
                        success ++;
                    }

                    List<Tbmqq440Vo> tbmqq440VoList = tbmqq440Mapper.getInvoiceOrderScheduleQty(new ArrayList(updateOrderList));
                    for (Tbmqq440Vo tbmqq440Vo: tbmqq440VoList) {
                        tbmqq440Vo.setUpdatedate(nowDate.getDateStr());
                        tbmqq440Vo.setUpdatetime(nowDate.getTimeStr());
                        tbmqq440Vo.setUpdateuser(SysCodeEnum.JOB.code);
                        tbmqq440Mapper.updateInvoiceOrderScheduleQty(tbmqq440Vo);
                    }


                    logger.info(String.format("订单明细开票进度同步第%d页: 共%d个, 成功%d个, 失败%d个",  i, totalCount, success, totalCount - success));

                }else{

                    logger.info(String.format("订单明细开票进度同步第%d页: 查询不到数据", totalCount));
                }
            }

        }else{
            logger.info(String.format("暂无订单明细开票进度需要同步"));
        }

    }

    // OA审批流结束回调处理
    @Transactional(rollbackFor = Exception.class)
    public OAUpdateCallBackResultVo afterApproval (AfterApprovalRequest request) {

        logger.info(String.format("OA审批流结束回调处理参数：%s", new Gson().toJson(request)));

        OAUpdateCallBackResultVo oaUpdateCallBackResultVo = new OAUpdateCallBackResultVo();
        oaUpdateCallBackResultVo.setCode("0000");
        oaUpdateCallBackResultVo.setMessage("处理成功");

        if (request.getApproveStatus().equals("F") || request.getApproveStatus().equals("N")) {
            String status = OrderStatusEnum.status_20.getCode();
            String oldStatus = OrderStatusEnum.status_10.getCode();

            String detailStatus = OrderDetailStatusEnum.status_20.getCode(); ;
            String oldDetailStatus = OrderDetailStatusEnum.status_10.getCode();;

            if (request.getApproveStatus().equals("F")) {
                status = OrderStatusEnum.status_15.getCode();
                detailStatus = OrderDetailStatusEnum.status_15.getCode();
            }


            Approve approve = new Approve();
            approve.setApprovecode(request.getBusinessId());

            Date approveNodeDate = new Date(Long.valueOf(request.getApproveNodeDate()));
            approve.setApprovedate(DateUtil.format(approveNodeDate, "yyyyMMdd"));
            approve.setApprovetime(DateUtil.format(approveNodeDate, "HHmmss"));

            int count = approveMapper.updateByPrimaryKeySelective(approve);
            if (count != 1) {
                throw new NetPlusException(String.format("审批单%s, OA审批流结束回调处理失败", request.getBusinessId()));
            }

            List<Tbmqq440Vo> tbmqq440VoList = tbmqq440Mapper.getOrderNoByApproveCode(request.getBusinessId());
            if (tbmqq440VoList.size() <= 0) {
                throw new NetPlusException(String.format("审批单%s, 对应的订单不存在", request.getBusinessId()));
            }

            for (Tbmqq440Vo tbmqq440Vo: tbmqq440VoList) {

                ChangeOrderStatusRequest changeOrderStatusRequest = new ChangeOrderStatusRequest();
                changeOrderStatusRequest.setNewStatus(status);
                changeOrderStatusRequest.setOldStatus(oldStatus);
                changeOrderStatusRequest.setOrderNo(tbmqq440Vo.getOrderno());
                changeOrderStatusRequest.setOpUser(SysCodeEnum.OA.code);
                changeOrderStatus(changeOrderStatusRequest);

                ChangeOrderDetailStatusRequest changeOrderDetailStatusRequest = new ChangeOrderDetailStatusRequest();
                changeOrderDetailStatusRequest.setOrderNo(tbmqq440Vo.getOrderno());
                changeOrderDetailStatusRequest.setNewStatus(detailStatus);
                changeOrderDetailStatusRequest.setOldStatus(oldDetailStatus);
                changeOrderDetailStatusRequest.setOpUser(SysCodeEnum.OA.code);
                changeOrderDetailStatus(changeOrderDetailStatusRequest);
            }

            //审批通过发送消息
            if (request.getApproveStatus().equals("F")) {

                //发送站内消息
                List<SendMsgRequest> sendMsgRequestList = new ArrayList();
                List<String> supplierNoList = new ArrayList();

                for (Tbmqq440Vo tbmqq440Vo: tbmqq440VoList) {

                    SendMsgRequest sendMsgRequest = new SendMsgRequest();
                    sendMsgRequest.setSendUserNo(tbmqq440Vo.getUserno());
                    sendMsgRequest.setReceiveUserNo('M'+ tbmqq440Vo.getSupplierno());
                    sendMsgRequest.setMsgType(MsgTypeEnum.SUBMIT_ORDER);
                    sendMsgRequest.setUrl("/mall/supplier/saleOrder");
                    sendMsgRequest.setParams(new String[]{tbmqq440Vo.getUserno(), tbmqq440Vo.getUsername(), tbmqq440Vo.getOrderno()});
                    sendMsgRequestList.add(sendMsgRequest);

                    supplierNoList.add(tbmqq440Vo.getSupplierno());
                }

                messagingRest.sendMsg(sendMsgRequestList);



                //短信发送
                List<Tbdu01Vo> supplierInfo = provideRest.getSupplierNoList(supplierNoList);
                if (ObjectUtil.nonEmpty(supplierInfo)) {

                    Map<String, Long> supplierNoGroup = supplierNoList
                            .stream()
                            .collect(Collectors.groupingBy(t -> t, Collectors.counting()));

                    for (Tbdu01Vo tbdu01Vo: supplierInfo) {

                        if (!StringUtils.isEmpty(tbdu01Vo.getBizcontactphone())) {

                            try {

                                //发送短信
                                JK0013Enum acceptOrderInformEmp = JK0013Enum.ACCEPT_ORDER_INFORM_EMP;
                                Map<String, String> params = acceptOrderInformEmp.getParams();
                                params.put("1",tbmqq440VoList.get(0).getUserno());
                                params.put("2",tbmqq440VoList.get(0).getUsername());
                                params.put("3",String.valueOf(supplierNoGroup.get(tbdu01Vo.getCompno())));

                                String reqId = UuidUtil.getUuid();

                                BaseRequest<JK0013Request> request2 = new BaseRequest<JK0013Request>();
                                request2.setReqId(reqId);
                                request2.setReqTime(String.valueOf(System.currentTimeMillis()));

                                JK0013Request jk0013Request = new JK0013Request();
                                jk0013Request.setReceiver(Arrays.asList(tbdu01Vo.getBizcontactphone()));
                                jk0013Request.setTemplateId(acceptOrderInformEmp.getTemplateId());
                                jk0013Request.setTemplateParas(params);
                                request2.setReqData(jk0013Request);

                                erpOutRest.JK0013(request2);

                            }catch (Exception e) {
                                logger.info(String.format("短信发送失败：%s", e.getMessage()));
                            }
                        }
                    }
                }

            }else{

                List<SendMsgRequest> sendMsgRequestList = new ArrayList();


                for (Tbmqq440Vo tbmqq440Vo: tbmqq440VoList) {

                    SendMsgRequest sendMsgRequest = new SendMsgRequest();
                    sendMsgRequest.setSendUserNo(request.getApproveCode());
                    sendMsgRequest.setReceiveUserNo(tbmqq440Vo.getUserno());
                    sendMsgRequest.setMsgType(MsgTypeEnum.ORDER_REJECT);
                    sendMsgRequest.setUrl("/mall/purchaseOrder");
                    sendMsgRequest.setParams(new String[]{tbmqq440Vo.getOrderno(), request.getApproveCode(), request.getApprovePeople()});
                    sendMsgRequestList.add(sendMsgRequest);

                }

                messagingRest.sendMsg(sendMsgRequestList);

            }

        }else{

            oaUpdateCallBackResultVo.setMessage(String.format("审批单%s, 流程未结束或驳回，不做处理", request.getBusinessId()));

        }

        return oaUpdateCallBackResultVo;
    }

    // OA审批流详情查询回调
    public OAQueryCallBackResultVo getApproveDetail (String approveCode) {

        OAQueryCallBackResultVo oaQueryCallBackResultVo = new OAQueryCallBackResultVo();
        oaQueryCallBackResultVo.setCode("0000");
        oaQueryCallBackResultVo.setMessage("请求成功");

        Approve approve = approveMapper.selectByPrimaryKey(approveCode);
        Tbmqq440Vo tbmqq440Vo = tbmqq440Mapper.getOrderByApproveCode(approveCode).get(0);
        List<Map<String, Object>> result = new ArrayList<>();

        //主信息
        Map<String, Object> labelMap = new HashMap<>();
        List<Map<String, String>> labelList = new ArrayList<>();

        String remainBudgetStr = "";
        BigDecimal remainBudget = approve.getRemainbudget();
        if (ObjectUtil.nonEmpty(remainBudget)) {
            remainBudgetStr = NumberUtil.div2Amt(remainBudget, new BigDecimal("10000")).toString();
        }

        labelList.add(getLabelMap("0", "采购单位", tbmqq440Vo.getBuyername()));
        labelList.add(getLabelMap("0", "采购部门", tbmqq440Vo.getDeptname()));
        labelList.add(getLabelMap("0", "部门条线", tbmqq440Vo.getLinename()));
        labelList.add(getLabelMap("0", "剩余预算/万元", remainBudgetStr));
        labelList.add(getLabelMap("0", "申请人", tbmqq440Vo.getUsername()));
        labelList.add(getLabelMap("0", "提交日期", DateUtil.format(tbmqq440Vo.getCreatedate(), "yyyyMMdd", "yyyy-MM-dd")));

        labelMap.put("type", "label");
        labelMap.put("datas", labelList);


        //明细信息
        Map<String, Object> gridMap = new HashMap();
        List<Map<String, Object>> gridDataList = new ArrayList<>();

        Map<String, Object> columnMap = new HashMap<>();
        List<Map<String, String>> columnList = new ArrayList<>();
        columnList.add(getContentMap("订单项次号"));
        columnList.add(getContentMap("物资名称"));
        columnList.add(getContentMap("型号规格"));
        columnList.add(getContentMap("含税单价/元"));
        columnList.add(getContentMap("同类最低单价/元"));
        columnList.add(getContentMap("计量单位"));
        columnList.add(getContentMap("订单数量"));
        columnList.add(getContentMap("库存数量"));
        columnList.add(getContentMap("库存上限"));
        columnList.add(getContentMap("工程项目单"));
        columnList.add(getContentMap("新增设备申请单"));
        columnList.add(getContentMap("工装设备简号"));
        columnList.add(getContentMap("总价"));
        columnList.add(getContentMap("高价备注"));
        columnList.add(getContentMap("备注"));

        columnMap.put("color", "#f4faff");
        columnMap.put("column", columnList);
        gridDataList.add(columnMap);

        List<Tbmqq441Vo> tbmqq441VoList = tbmqq441Mapper.getApproveDetail(approveCode);
        for (Tbmqq441Vo tbmqq441Vo : tbmqq441VoList) {

            Map<String, Object> contentMap = new HashMap();
            List<Map<String, String>> contentList = new ArrayList();

            contentList.add(getContentMap(tbmqq441Vo.getOrderno()+"-"+tbmqq441Vo.getOrderdetailno()));
            contentList.add(getContentMap(tbmqq441Vo.getProductname()));
            contentList.add(getContentMap(tbmqq441Vo.getSpec()));
            contentList.add(getContentMap(tbmqq441Vo.getPrice().toString()));
            contentList.add(getContentMap(tbmqq441Vo.getLowprice().toString()));
            contentList.add(getContentMap(tbmqq441Vo.getQtyunit()));
            contentList.add(getContentMap(tbmqq441Vo.getQty().toString()));
            contentList.add(getContentMap(tbmqq441Vo.getCkqty().toString()));
            contentList.add(getContentMap(tbmqq441Vo.getQtyupper().toString()));
            contentList.add(getContentMap(tbmqq441Vo.getProjectname()+tbmqq441Vo.getProjectno()));
            contentList.add(getContentMap(tbmqq441Vo.getDeviceapplyno()));
            contentList.add(getContentMap(tbmqq441Vo.getDevicesimpleno()));
            contentList.add(getContentMap(tbmqq441Vo.getAmt().toString()));
            contentList.add(getContentMap(tbmqq441Vo.getRemark()));
            contentList.add(getContentMap(tbmqq441Vo.getRemark2()));

            contentMap.put("column", contentList);
            contentMap.put("color", "#f4faff");
            gridDataList.add(contentMap);
        }

        gridMap.put("type", "grid");
        gridMap.put("datas", gridDataList);

        result.add(labelMap);
        result.add(gridMap);

        oaQueryCallBackResultVo.setResult(result);
        return oaQueryCallBackResultVo;

    }

    private Map<String, String> getLabelMap (String isLong, String name, String value) {
        Map<String, String> map = new HashMap();
        map.put("isLong", isLong);
        map.put("name", name);
        map.put("value", StringUtils.isEmpty(value) ? "": value);
        return map;
    }

    private Map<String, String> getContentMap (String content) {
        Map<String, String> map = new HashMap();
        map.put("content", StringUtils.isEmpty(content) ? "": content);
        return map;
    }

    //仓库撤销订单明细
    @Transactional(rollbackFor = Exception.class)
    public void orderDetailCancel (OrderDetailCancelRequest request) {

        NowDate nowDate = new NowDate();
        Tbmqq441Vo tbmqq441Vo = tbmqq441Mapper.getOrderNoById(request.getOrderDetailId());


        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_OUT_SUPPLIER.code);

        if (tbmqq461.getVal().equals(tbmqq441Vo.getSupplierno())) {
            throw new NetPlusException("第三方供应商订明细不可以撤销");
        }

        if (StringUtils.isEmpty(tbmqq441Vo.getOrderno())) {
            throw new NetPlusException(String.format("订单明细id:%s, 不存在", request.getOrderDetailId()));
        }

        int orderDetailUpdateCount = tbmqq441Mapper.updateOrderDetailCancelStatus(request.getOrderDetailId());
        if (orderDetailUpdateCount != 1) {
            throw new NetPlusException(String.format("订单明细id:%s, 撤销失败", request.getOrderDetailId()));
        }

        Tbmqq440Vo tbmqq440Vo = tbmqq440Mapper.getOrderAmtAndQty(tbmqq441Vo.getOrderno());
        tbmqq440Vo.setOrderno(tbmqq441Vo.getOrderno());
        tbmqq440Vo.setUpdateuser(SysCodeEnum.CK.code);
        tbmqq440Vo.setUpdatedate(nowDate.getDateStr());
        tbmqq440Vo.setUpdatetime(nowDate.getTimeStr());

        int orderUpdateCount = tbmqq440Mapper.updateOrderAmtAndQty(tbmqq440Vo);
        if (orderUpdateCount != 1) {
            throw new NetPlusException(String.format("订单:%s, 更新失败", tbmqq441Vo.getOrderno()));
        }

        int count = tbmqq441Mapper.checkOrderIsCancel(tbmqq441Vo.getOrderno());
        if (count == 0) {

            //撤销明细后，订单是否撤销
            ChangeOrderStatusRequest changeOrderStatusRequest = new ChangeOrderStatusRequest();
            changeOrderStatusRequest.setNewStatus(OrderStatusEnum.status_35.getCode());
            changeOrderStatusRequest.setOldStatus(OrderStatusEnum.status_25.getCode());
            changeOrderStatusRequest.setOrderNo(tbmqq441Vo.getOrderno());
            changeOrderStatusRequest.setOpUser(SysCodeEnum.CK.code);
            changeOrderStatus(changeOrderStatusRequest);

        }else{

            //撤销明细后，订单是否完结
            int count2 = tbmqq441Mapper.checkOrderIsOver(tbmqq441Vo.getOrderno());
            if (count2 == 0) {
                ChangeOrderStatusRequest changeOrderStatusRequest = new ChangeOrderStatusRequest();
                changeOrderStatusRequest.setNewStatus(OrderStatusEnum.status_30.getCode());
                changeOrderStatusRequest.setOldStatus(OrderStatusEnum.status_25.getCode());
                changeOrderStatusRequest.setOrderNo(tbmqq441Vo.getOrderno());
                changeOrderStatusRequest.setOpUser(SysCodeEnum.CK.code);
                changeOrderStatus(changeOrderStatusRequest);
            }

        }
    }

    @NotNull
    private List<String> mallInventoryOrderStatus() {
        List<String> statusList=new ArrayList<>();
        statusList.add(OrderDetailStatusEnum.status_10.getCode());
        statusList.add(OrderDetailStatusEnum.status_15.getCode());
        return statusList;
    }

    public Map<String, BigDecimal> getMallInventoryByMatrlId(List<String> matrlIdList){
        if(ObjectUtils.isEmpty(matrlIdList)){
            return null;
        }
        List<String> statusList = mallInventoryOrderStatus();
        List<KeyValue> mallInventoryList = tbmqq441Mapper.getMallInventoryByMatrlId(matrlIdList, statusList);
        Map<String, BigDecimal> collect = mallInventoryList.stream().collect(Collectors.toMap(KeyValue::getKey, e -> new BigDecimal(e.getValue()), (e1, e2) -> e2));
        return collect;
    }

    public PageBean<Tbmqq441Vo> getMallGoodsInventoryDetailByMatrlId(MallGoodsInventoryPageRequest request){
        if(StringUtils.isEmpty(request.getMatrlId())){
            return null;
        }

        List<String> ids=new ArrayList<>();
        ids.add(request.getMatrlId());

        List<String> statusList = mallInventoryOrderStatus();
        List<Tbmqq441Vo> mallInventoryList = tbmqq441Mapper.getMallGoodsInventoryDetailByMatrlId(ids, statusList,request.getPageIndex(),request.getPageSize());
        mallInventoryList.forEach(t -> {
            t.setStatusName(OrderDetailStatusEnum.getName(t.getStatus()));
        });

        int count = tbmqq441Mapper.getMallGoodsInventoryDetailCountByMatrlId(ids, statusList);

        PageBean<Tbmqq441Vo> page = new PageBean<>();
        page.setItems(mallInventoryList);
        page.setTotalCount(count);
        return page;

    }

    //作废订单
    @Transactional(rollbackFor = Exception.class)
    public void invalidOrder(InvalidOrderRequest request) {
        if(StringUtils.isEmpty(request.getOrderNo())){
            throw new NetPlusException("订单号不能为空!");
        }
        if(StringUtils.isEmpty(request.getInvalidReason())){
            throw new NetPlusException("作废原因不能为空!");
        }
        Tbmqq440 order = tbmqq440Mapper.selectByPrimaryKey(request.getOrderNo());
        if(ObjectUtils.isEmpty(order)){
            throw new NetPlusException("订单未找到!");
        }
        if(!Objects.equals(order.getStatus(),OrderStatusEnum.status_15.getCode())){
            throw new NetPlusException("订单状态不是待接单,无法作废!");
        }

        LoginUserBean user = identityRest.getCurrentUser();
        NowDate nowDate = new NowDate();
        Map param = new HashMap<>();
        param.put("orderno",request.getOrderNo());
        param.put("invalidreason",request.getInvalidReason());
        param.put("status",OrderStatusEnum.status_40.getCode());
        param.put("oldstatus",OrderStatusEnum.status_15.getCode());
        param.put("invaliduser",user.getUserCode());
        param.put("invalidusername",user.getUsername());
        param.put("invaliddate",nowDate.getDateStr());
        param.put("invalidtime",nowDate.getTimeStr());
        param.put("updateuser",user.getUserCode());
        param.put("updatedate",nowDate.getDateStr());
        param.put("updatetime",nowDate.getTimeStr());
        int updateCount = tbmqq440Mapper.updateOrderStatus(param);
        if(updateCount != 1){
            throw new NetPlusException("作废失败!");
        }

        param.put("status",OrderDetailStatusEnum.status_45.getCode());
        tbmqq441Mapper.updateOrderDetailStatus(param);
    }

    public void orderNotReceiveWarn() {
        //获取配置的提醒时间参数
        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.ORDER_NOT_RECEIVE_WARN_TIMES.code);
        if(ObjectUtils.isEmpty(tbmqq461) || StringUtils.isEmpty(tbmqq461.getVal())){
            return;
        }
        List<Integer> warnTimes = Arrays.stream(tbmqq461.getVal().split(","))
                .map(e -> Integer.valueOf(e))
                .sorted(Integer::compareTo)
                .collect(Collectors.toList());

        //筛选出符合提醒条件的订单
        List<Tbmqq440Vo> needWarnOrderList=tbmqq440Mapper.selectNeedWarnOrder(warnTimes);
        if(ObjectUtils.isEmpty(needWarnOrderList)){
            return;
        }

        //根据需要提醒的订单获取到需要提醒的供应商
        List<String> needWarnSuppliers = needWarnOrderList.stream().map(Tbmqq440Vo::getSupplierno).collect(Collectors.toList());

        //找到需要提醒的供应商所有待接单状态的订单
        List<Tbmqq440Vo> notReceiveOrderBySupplier= tbmqq440Mapper.selectNotReceiveOrderBySupplierNo(needWarnSuppliers);

        //将需要提醒的供应商待接单状态的订单数量按照下单人分组获取数量(因为发送短信给供应商的内容为你有xxx的xxx条订单待接单)
        Map<String, Map<String, Long>> supplierWarnGroup = notReceiveOrderBySupplier.stream()
                .collect(
                        Collectors.groupingBy(Tbmqq440Vo::getSupplierno,
                                Collectors.groupingBy(Tbmqq440Vo::getUserno, Collectors.counting())
                        )
                );

        //缓存用户信息
        Map<String,Tbdu01Vo> infoMap=new HashMap<>();

        //遍历给供应商发短信
        supplierWarnGroup.forEach((supplierNo,v) -> {
            //获取供应商的信息
            Tbdu01Vo supplierInfo = infoMap.get(supplierNo);
            if(ObjectUtils.isEmpty(supplierInfo)){
                GetSupplierNoRequest req = new GetSupplierNoRequest();
                req.setSupplierNo(supplierNo);
                supplierInfo = provideRest.getSupplierNo(req);
                infoMap.put(supplierNo,supplierInfo);
            }

            Tbdu01Vo finalSupplierInfo = supplierInfo;

            //遍历供应商有哪些下单人的订单待接单
            v.forEach((userNo, count) -> {
                //获取下单人信息
                Tbdu01Vo userInfo = infoMap.get(userNo);
                if(ObjectUtils.isEmpty(userInfo)){
                    GetUserInfoRequest getUserInfoRequest = new GetUserInfoRequest();
                    getUserInfoRequest.setUserNo(userNo);
                    userInfo = identityRest.getUserByUserNo(getUserInfoRequest);
                    infoMap.put(userNo,userInfo);
                }
                //发送短信
                sendAcceptOrderWarn(userNo,userInfo.getName(),count, finalSupplierInfo.getBizcontactphone());
            });
        });

        //根据需要提醒的订单获取到需要提醒的采购人信息
        List<String> needWarnOrderNo = needWarnOrderList.stream().map(Tbmqq440Vo::getOrderno).collect(Collectors.toList());
        List<AgentInfoVo> agentInfoVoList = tbmqq441Mapper.getAgentInfoByOrderNo(needWarnOrderNo);
        if(ObjectUtils.isEmpty(agentInfoVoList)){
            return;
        }
        List<String> agentNoList = agentInfoVoList.stream().map(AgentInfoVo::getAgentno).collect(Collectors.toList());
        Map<String, String> agentInfoMap = agentInfoVoList.stream()
                .filter(e -> !StringUtils.isEmpty(e.getAgentno()) && !StringUtils.isEmpty(e.getAgentphone()) )
                .collect(
                        Collectors.toMap(
                                AgentInfoVo::getAgentno,
                                AgentInfoVo::getAgentphone,
                                (e1, e2) -> e1
                        )
                );

        //查询采购人需要提醒的数据
        List<AgentWarnVo> agentWarnVoList = tbmqq441Mapper.getWarnOrderInfoByAgent(agentNoList);

        //将采购人需要提醒的数据按采购人、供应商、审批时间分组
        Map<String, Map<String, Map<String, Long>>> agentWarnGroup = agentWarnVoList.stream().collect(
                Collectors.groupingBy(AgentWarnVo::getAgentno,
                        Collectors.groupingBy(AgentWarnVo::getSupplierno,
                                Collectors.groupingBy(AgentWarnVo::getTimeoutdate, Collectors.counting())
                        )
                )
        );

        //发送采购人提醒短信
        agentWarnGroup.forEach((agentNo,v) -> {
            v.forEach((supplierNo,v1) -> {
                Tbdu01Vo supplierInfo = infoMap.get(supplierNo);
                if(ObjectUtils.isEmpty(supplierInfo)){
                    GetSupplierNoRequest req = new GetSupplierNoRequest();
                    req.setSupplierNo(supplierNo);
                    supplierInfo = provideRest.getSupplierNo(req);
                    infoMap.put(supplierNo,supplierInfo);
                }

                String supplierName = supplierNo + "-" + supplierInfo.getName();
                v1.forEach((timeOutDate,count) -> {
                    sendAgentWarn(supplierName,count,timeOutDate,agentInfoMap.get(agentNo));
                });
            });
        });

    }

    private void sendAgentWarn(String supplier,Long orderSum,String timeOutDate,String receiverPhone){
        logger.info("发送物资采购人提醒信息 supplier = " + supplier + ", orderSum = " + orderSum + ", timeOutDate = " + timeOutDate + ", receiverPhone = " + receiverPhone);
        try {
            if(StringUtils.isEmpty(receiverPhone)){
                return;
            }
            //发送短信
            JK0013Enum acceptOrderInformEmp = JK0013Enum.AGENT_ACCEPT_ORDER_WARN;
            Map<String, String> params = acceptOrderInformEmp.getParams();
            params.put("1",supplier);
            params.put("2",String.valueOf(orderSum));
            params.put("3",timeOutDate);

            String reqId = UuidUtil.getUuid();

            BaseRequest<JK0013Request> request2 = new BaseRequest<JK0013Request>();
            request2.setReqId(reqId);
            request2.setReqTime(String.valueOf(System.currentTimeMillis()));

            JK0013Request jk0013Request = new JK0013Request();
            jk0013Request.setReceiver(Arrays.asList(receiverPhone));
            jk0013Request.setTemplateId(acceptOrderInformEmp.getTemplateId());
            jk0013Request.setTemplateParas(params);
            request2.setReqData(jk0013Request);

            erpOutRest.JK0013(request2);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendAcceptOrderWarn(String userNo,String userName,Long orderSum,String receiverPhone){
        logger.info("发送供应商接单信息 userNo = " + userNo + ", userName = " + userName + ", orderSum = " + orderSum + ", receiverPhone = " + receiverPhone);
        try {
            if(StringUtils.isEmpty(receiverPhone)){
                return;
            }
            //发送短信
            JK0013Enum acceptOrderInformEmp = JK0013Enum.ACCEPT_ORDER_INFORM_EMP;
            Map<String, String> params = acceptOrderInformEmp.getParams();
            params.put("1",userNo);
            params.put("2",userName);
            params.put("3",String.valueOf(orderSum));

            String reqId = UuidUtil.getUuid();

            BaseRequest<JK0013Request> request2 = new BaseRequest<JK0013Request>();
            request2.setReqId(reqId);
            request2.setReqTime(String.valueOf(System.currentTimeMillis()));

            JK0013Request jk0013Request = new JK0013Request();
            jk0013Request.setReceiver(Arrays.asList(receiverPhone));
            jk0013Request.setTemplateId(acceptOrderInformEmp.getTemplateId());
            jk0013Request.setTemplateParas(params);
            request2.setReqData(jk0013Request);

            erpOutRest.JK0013(request2);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
