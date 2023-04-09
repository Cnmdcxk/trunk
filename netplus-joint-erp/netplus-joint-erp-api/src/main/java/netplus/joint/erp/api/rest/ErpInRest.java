package netplus.joint.erp.api.rest;

import netplus.component.entity.aspect.InterfaceAnnotation;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.BaseResponse;
import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.joint.erp.api.Urls;
import netplus.joint.erp.api.request.in.JK0008.JK0008Request;
import netplus.joint.erp.api.request.out.JK0014Request;
import netplus.joint.erp.api.response.in.JK0008.JK0008Response;
import netplus.joint.erp.api.response.in.JK0014Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(
        name = "netplus-joint-erp-in-service",
        url = "${service.netplus-joint-erp-in-service}",
        contextId = "ErpInRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface ErpInRest {

    @Anonymous
    @PostMapping(Urls.JK0008)
    @InterfaceAnnotation(caller = "ERP", callee = "PT")
    BaseResponse<JK0008Response> JK0008(@RequestBody BaseRequest<JK0008Request> request) ;

    @Anonymous
    @PostMapping(Urls.JK0014)
    @InterfaceAnnotation(caller = "ERP", callee = "PT")
    BaseResponse<JK0014Response> JK0014(@RequestBody BaseRequest<List<JK0014Request>> request);

}
