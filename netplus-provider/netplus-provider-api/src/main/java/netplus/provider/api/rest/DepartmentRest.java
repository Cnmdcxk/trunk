package netplus.provider.api.rest;

import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.provider.api.Urls;
import netplus.provider.api.pojo.ygmalluser.Department;
import netplus.provider.api.request.department.GetDepartmentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "netplus-provider-service",
        url = "${service.netplus-provider-service}",
        contextId = "DepartmentRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface DepartmentRest {


    @PostMapping(Urls.syncDepartment)
    void syncDepartment ();


    @PostMapping(Urls.getDepartment)
    Department getDepartment (@RequestBody GetDepartmentRequest request);

}
