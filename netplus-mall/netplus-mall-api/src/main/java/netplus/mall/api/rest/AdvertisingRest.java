package netplus.mall.api.rest;

import netplus.component.entity.response.ApiResponse;
import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.mall.api.Urls;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "netplus-mall-service",
        url = "${service.netplus-mall-service}",
        contextId = "AdvertisingRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface AdvertisingRest {

    /**
     * 获取所有已发布的广告
     * @return
     */
    @PostMapping(Urls.Advertising.getPublishAdvertising)
    ApiResponse getPublishAdvertising();
}
