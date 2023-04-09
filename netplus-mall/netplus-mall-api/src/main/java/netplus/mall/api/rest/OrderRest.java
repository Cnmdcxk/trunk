package netplus.mall.api.rest;

import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.mall.api.Urls;
import netplus.mall.api.request.order.OrderDetailCancelRequest;
import netplus.mall.api.request.order.SyncOrderDetailScheduleRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "netplus-mall-service",
        url = "${service.netplus-mall-service}",
        contextId = "OrderRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface OrderRest  {

    @PostMapping(Urls.Order.syncOrderDetailSchedule)
    void syncOrderDetailSchedule(@RequestBody SyncOrderDetailScheduleRequest request);


    @PostMapping(Urls.Order.orderDetailCancel)
    void orderDetailCancel(@RequestBody OrderDetailCancelRequest request);


    @PostMapping(Urls.Order.syncInvoiceOrderDetailSchedule)
    void syncInvoiceOrderDetailSchedule(@RequestBody SyncOrderDetailScheduleRequest request);

    @PostMapping(Urls.Order.orderNotReceiveWarn)
    void orderNotReceiveWarn();
}
