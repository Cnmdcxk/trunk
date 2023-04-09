package netplus.provider.api.rest;

import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.provider.api.Urls;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "netplus-provider-service",
        url = "${service.netplus-provider-service}",
        contextId = "ServiceConfigRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface ServiceConfigRest {


    @PostMapping(Urls.ServiceData.getServiceConfigByCode)
    Tbmqq461 getServiceConfigByCode (@RequestBody String code);
}
