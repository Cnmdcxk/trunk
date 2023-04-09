package netplus.mall.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.iface.BaseResponse;
import netplus.component.entity.response.ApiResponse;
import netplus.joint.erp.api.response.out.JK0004.JK0004SubResponse;
import netplus.joint.erp.api.response.out.JK0016.JK0016SubResponse;
import netplus.joint.erp.api.response.out.JK0022.JK0022Response;
import netplus.mall.api.Urls;
import netplus.mall.api.request.order.*;
import netplus.mall.api.rest.OrderRest;
import netplus.mall.api.vo.order.CreateOrderResultVo;
import netplus.mall.api.vo.order.OAQueryCallBackResultVo;
import netplus.mall.api.vo.order.OAUpdateCallBackResultVo;
import netplus.mall.api.vo.order.Tbmqq440Vo;
import netplus.mall.service.biz.CreateOrderBiz;
import netplus.mall.service.biz.IfaceBiz;
import netplus.mall.service.biz.OrderBiz;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.rest.ServiceConfigRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController  extends BasedController implements OrderRest {

    @Autowired
    OrderBiz orderBiz;

    @Autowired
    CreateOrderBiz createOrderBiz;

    @Autowired
    ServiceConfigRest serviceConfigRest;

    @Autowired
    IfaceBiz ifaceBiz;

    @PostMapping(Urls.Order.createOrder)
    public ApiResponse<CreateOrderResultVo> createOrder(@RequestBody CreateOrderRequest request) {
        return createOrderBiz.createOrder(request);
    }

    @PostMapping(Urls.Order.getOrderList)
    public PageBean<Tbmqq440Vo> getOrderList(@RequestBody GetOrderListRequest request) {
        return orderBiz.getOrderList(request);
    }

    @PostMapping(Urls.Order.getOrderAndDetailList)
    public PageBean<Tbmqq440Vo> getOrderAndDetailList(@RequestBody GetOrderListRequest request) {
        return orderBiz.getOrderAndDetailList(request);
    }

    @PostMapping(Urls.Order.getMyWaitingTakeOrder)
    public int getMyWaitingTakeOrder(){
        return orderBiz.getMyWaitingTakeOrder();
    }


    @PostMapping(Urls.Order.exportOrder)
    public String exportOrder(@RequestBody GetOrderListRequest request) throws Exception {
        return orderBiz.exportOrder(request);
    }

    @PostMapping(Urls.Order.exportOrderDetail)
    public String exportOrderDetail(@RequestBody GetOrderListRequest request) throws Exception {
        return orderBiz.exportOrderDetail(request);
    }

    @PostMapping(Urls.Order.getOrderInfo)
    public Tbmqq440Vo getOrderInfo(@RequestBody GetOrderInfoRequest request) throws Exception {
        return orderBiz.getOrderInfo(request);
    }


    @PostMapping(Urls.Order.getTabCount)
    public Map<String, Object> getTabCount(@RequestBody GetOrderListRequest request) throws Exception {
        return orderBiz.getTabCount(request);
    }

    @Anonymous
    @PostMapping(Urls.Order.afterApproval)
    public OAUpdateCallBackResultVo afterApproval(@RequestBody AfterApprovalRequest request) {
        return orderBiz.afterApproval(request);
    }

    @Anonymous
    @GetMapping(Urls.Order.getApproveDetail)
    public OAQueryCallBackResultVo getApproveDetail(@RequestParam("businessId") String businessId) {
        return orderBiz.getApproveDetail(businessId);
    }

    @Anonymous
    @PostMapping(Urls.Order.syncOrderDetailSchedule)
    public void syncOrderDetailSchedule(@RequestBody SyncOrderDetailScheduleRequest request) {
        orderBiz.syncOrderDetailSchedule(request);
    }

    @PostMapping(Urls.Order.receivingOrder)
    public void receivingOrder(@RequestBody ReceivingOrderRequest request) {
        orderBiz.receivingOrder(request);
    }

    @PostMapping(Urls.Order.viewApproveProgress)
    public BaseResponse<JK0022Response> viewApproveProgress(@RequestBody ViewApproveProgressRequest request){
        return ifaceBiz.viewApproveProgress(request);
    }

    @Anonymous
    @PostMapping(Urls.Order.getLineList)
    public List<JK0004SubResponse> getLineList() {
        return ifaceBiz.getLineList();
    }


    @Anonymous
    @PostMapping(Urls.Order.orderDetailCancel)
    public void orderDetailCancel(@RequestBody OrderDetailCancelRequest request) {
         orderBiz.orderDetailCancel(request);
    }


    @Anonymous
    @PostMapping(Urls.Order.syncInvoiceOrderDetailSchedule)
    public void syncInvoiceOrderDetailSchedule(@RequestBody SyncOrderDetailScheduleRequest request) {
        orderBiz.syncInvoiceOrderDetailSchedule(request);
    }

    @PostMapping(Urls.Order.getDeliveryAppointmentUrl)
    public String getDeliveryAppointmentUrl(){
        Tbmqq461 info = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.DELIVERY_APPOINTMENT_URL.code);
        if(!ObjectUtils.isEmpty(info)){
            return info.getVal();
        }
        return "";
    }

    @Anonymous
    @PostMapping(Urls.Order.getSprInfo)
    public JK0016SubResponse getSprInfo(@RequestBody GetSprInfoRequest request){
        return ifaceBiz.getSprInfo(request);
    }

    @PostMapping(Urls.Order.invalidOrder)
    public void invalidOrder(@RequestBody InvalidOrderRequest request){
        orderBiz.invalidOrder(request);
    }

    @Override
    @Anonymous
    @PostMapping(Urls.Order.orderNotReceiveWarn)
    public void orderNotReceiveWarn(){
        orderBiz.orderNotReceiveWarn();
    }
}
