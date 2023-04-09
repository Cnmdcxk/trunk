package netplus.messaging.api.rest;

import netplus.component.entity.data.PageBean;
import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.messaging.api.Urls;
import netplus.messaging.api.request.CreateMsgRequest;
import netplus.messaging.api.request.MessagingRequest;
import netplus.messaging.api.request.SendMsgRequest;
import netplus.messaging.api.vo.MessagingVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "netplus-messaging-service",
        url = "${service.netplus-messaging-service}",
        contextId = "MessagingRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface MessagingRest {

    @PostMapping(Urls.Messaging.query)
    PageBean<MessagingVo> query(@RequestBody MessagingRequest messagingRequest);

    @PostMapping(Urls.Messaging.sendMsg)
    void sendMsg(@RequestBody List<SendMsgRequest> request);
}
