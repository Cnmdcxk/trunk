package netplus.provider.api.rest;


import netplus.component.entity.auth.Anonymous;
import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.joint.erp.api.response.out.JK0035.Data;
import netplus.provider.api.Urls;
import netplus.provider.api.request.GetSupplierNoRequest;
import netplus.provider.api.request.LoginRequest;
import netplus.provider.api.request.SyncProviderRequest;
import netplus.provider.api.vo.Tbdu01Vo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "netplus-provider-service",
        url = "${service.netplus-provider-service}",
//        url = "http://127.0.0.1:20007",

        contextId = "ProvideRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface ProvideRest {

    @PostMapping(Urls.syncProvider)
    void syncProvider (@RequestBody SyncProviderRequest request);

    @PostMapping(Urls.getYgUser)
    void getYgUser();

    @PostMapping(Urls.getSupplierNo)
    Tbdu01Vo getSupplierNo(@RequestBody GetSupplierNoRequest request);


    @PostMapping(Urls.getSupplierByName)
    Tbdu01Vo getSupplierByName(@RequestBody GetSupplierNoRequest request);

    @PostMapping(Urls.getSupplierNoList)
    List<Tbdu01Vo> getSupplierNoList(@RequestBody List<String> request);

}
