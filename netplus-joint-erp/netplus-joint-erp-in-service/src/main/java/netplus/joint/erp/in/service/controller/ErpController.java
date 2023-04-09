package netplus.joint.erp.in.service.controller;

import io.swagger.annotations.Api;
import netplus.component.authbase.BasedController;
import netplus.component.entity.aspect.InterfaceAnnotation;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.BaseResponse;
import netplus.joint.erp.api.Urls;
import netplus.joint.erp.api.request.in.JK0008.JK0008Request;
import netplus.joint.erp.api.request.in.JK0023Request;
import netplus.joint.erp.api.request.in.JK0025Request;
import netplus.joint.erp.api.request.in.JK0026.JK0026Request;
import netplus.joint.erp.api.request.out.JK0014Request;
import netplus.joint.erp.api.response.in.*;
import netplus.joint.erp.api.response.in.JK0008.JK0008Response;
import netplus.joint.erp.api.rest.ErpInRest;
import netplus.joint.erp.in.service.biz.ErpBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "erpIn接口")
@RestController
public class ErpController extends BasedController implements ErpInRest {


    @Autowired
    private ErpBiz erpBiz;

    @Anonymous
    @PostMapping(Urls.JK0008)
    @InterfaceAnnotation(caller = "ERP", callee = "PT")
    public BaseResponse<JK0008Response> JK0008(@RequestBody BaseRequest<JK0008Request> request) {

        return erpBiz.contractFrameworkChange(request);
    }

    @Anonymous
    @PostMapping(Urls.JK0014)
    @InterfaceAnnotation(caller = "ERP", callee = "PT")
    public BaseResponse<JK0014Response> JK0014(@RequestBody BaseRequest<List<JK0014Request>> request) {
        return erpBiz.syncProvider(request);
    }


    @Anonymous
    @PostMapping(Urls.JK0023)
    @InterfaceAnnotation(caller = "ERP", callee = "PT")
    public BaseResponse<JK0023Response> JK0023(@RequestBody BaseRequest<JK0023Request> request) {
        return erpBiz.JK0023(request);
    }


    @Anonymous
    @PostMapping(Urls.JK0025)
    @InterfaceAnnotation(caller = "ERP", callee = "PT")
    public BaseResponse<JK0025Response> JK0025(@RequestBody BaseRequest<JK0025Request> request) {
        return erpBiz.JK0025(request);
    }


    @Anonymous
    @PostMapping(Urls.JK0026)
    @InterfaceAnnotation(caller = "ERP", callee = "PT")
    public BaseResponse<JK0026Response> JK0026(@RequestBody BaseRequest<JK0026Request> request) {
        return erpBiz.JK0026(request);
    }


}
