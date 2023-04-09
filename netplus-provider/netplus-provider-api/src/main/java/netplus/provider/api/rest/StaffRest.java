package netplus.provider.api.rest;

import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.provider.api.Urls;
import netplus.provider.api.request.GetConsigneeRequest;
import netplus.provider.api.request.GetUserRoleListRequest;
import netplus.provider.api.vo.Tbdu01Vo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "netplus-provider-service",
        url = "${service.netplus-provider-service}",
        contextId = "StaffRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface StaffRest {

    @PostMapping(Urls.Staff.getConsigneeByUserNo)
    Tbdu01Vo getConsigneeByUserNo (@RequestBody GetConsigneeRequest request);


    @PostMapping(Urls.Staff.getUserRoleList)
    List<String> getUserRoleList (@RequestBody GetUserRoleListRequest request);


}
