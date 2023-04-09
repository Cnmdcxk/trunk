package netplus.mall.api.rest;

import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.mall.api.Urls;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "netplus-mall-service",
        url = "${service.netplus-mall-service}",
        contextId = "FutureGoodRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface FutureGoodRest {

    @PostMapping(Urls.FutureGood.batchImport)
    void batchImport(@RequestBody List<Tbmqq430> list);
}
