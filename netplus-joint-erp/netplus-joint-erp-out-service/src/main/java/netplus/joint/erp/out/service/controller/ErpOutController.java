package netplus.joint.erp.out.service.controller;

import io.swagger.annotations.Api;
import netplus.component.authbase.BasedController;
import netplus.component.entity.aspect.InterfaceAnnotation;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.BaseResponse;
import netplus.component.http.EasyHttpClient;
import netplus.joint.erp.api.Urls;
import netplus.joint.erp.api.request.in.JK0013Request;
import netplus.joint.erp.api.request.out.*;
import netplus.joint.erp.api.request.out.JK0010.JK0010Request;
import netplus.joint.erp.api.request.out.JK0011.JK0011Request;
import netplus.joint.erp.api.request.out.JK0012.JK0012Request;
import netplus.joint.erp.api.request.out.JK0018.JK0018Request;
import netplus.joint.erp.api.request.out.JK0032.JK0032Request;
import netplus.joint.erp.api.response.out.JK0034.JK0034Response;
import netplus.joint.erp.api.response.out.JK0035.JK0035Response;
import netplus.joint.erp.api.response.out.*;
import netplus.joint.erp.api.response.out.JK0003.JK0003Response;
import netplus.joint.erp.api.response.out.JK0004.JK0004Response;
import netplus.joint.erp.api.response.out.JK0005.JK0005Response;
import netplus.joint.erp.api.response.out.JK0006.JK0006Response;
import netplus.joint.erp.api.response.out.JK0007.JK0007Response;
import netplus.joint.erp.api.response.out.JK0010.JK0010Response;
import netplus.joint.erp.api.response.out.JK0012.JK0012Response;
import netplus.joint.erp.api.response.out.JK0013.JK0013Response;
import netplus.joint.erp.api.response.out.JK0015.JK0015Response;
import netplus.joint.erp.api.response.out.JK0016.JK0016Response;
import netplus.joint.erp.api.response.out.JK0017.JK0017Response;
import netplus.joint.erp.api.response.out.JK0022.JK0022Response;
import netplus.joint.erp.api.response.out.JK0024.JK0024Response;
import netplus.joint.erp.api.response.out.JK0028.JK0028Response;
import netplus.joint.erp.api.response.out.JK0031.JK0031Response;
import netplus.joint.erp.api.response.out.JK0033.JK0033Response;
import netplus.joint.erp.api.rest.ErpOutRest;
import netplus.joint.erp.out.service.biz.ErpOutBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "erpOut接口")
@RestController
public class ErpOutController extends BasedController implements ErpOutRest {

    @Autowired
    EasyHttpClient easyHttpClient;

    @Autowired
    ErpOutBiz erpOutBiz;

    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0001)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0001Response> JK0001(@RequestBody BaseRequest<JK0001Request> request) {
        return erpOutBiz.JK0001(request);
    }

    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0002)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0002Response> JK0002(@RequestBody BaseRequest<JK0002Request> request) {
        return erpOutBiz.JK0002(request);
    }
    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0003)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0003Response> JK0003(@RequestBody BaseRequest<JK0003Request> request) {
        return erpOutBiz.JK0003(request);
    }
    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0004)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0004Response> JK0004(@RequestBody BaseRequest<JK0004Request> request) {
        return erpOutBiz.JK0004(request);
    }
    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0005)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0005Response> JK0005(@RequestBody BaseRequest<JK0005Request> request) {
        return erpOutBiz.JK0005(request);
    }
    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0006)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0006Response> JK0006(@RequestBody BaseRequest<JK0006Request> request) {
        return erpOutBiz.JK0006(request);
    }
    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0007)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0007Response> JK0007(@RequestBody BaseRequest<JK0007Request> request) {
        return erpOutBiz.JK0007(request);
    }

    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0009)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0009Response> JK0009(@RequestBody BaseRequest<JK0009Request> request) {
        return erpOutBiz.JK0009(request);
    }

    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0010)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0010Response> JK0010(@RequestBody BaseRequest<JK0010Request> request) {
        return erpOutBiz.JK0010(request);
    }

    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0011)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0011Response> JK0011(@RequestBody BaseRequest<JK0011Request> request) {
        return erpOutBiz.JK0011(request);
    }

    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0012)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0012Response> JK0012(@RequestBody BaseRequest<JK0012Request> request) {
        return erpOutBiz.JK0012(request);
    }

    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0013)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0013Response> JK0013(@RequestBody BaseRequest<JK0013Request> request) {
        return erpOutBiz.JK0013(request);
    }

    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0015)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0015Response> JK0015(@RequestBody BaseRequest<JK0015Request> request) {
        return erpOutBiz.JK0015(request);

    }

    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0016)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0016Response> JK0016(@RequestBody BaseRequest<JK0016Request> request) {
        return erpOutBiz.JK0016(request);

    }

    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0017)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0017Response> JK0017(@RequestBody BaseRequest<JK0017Request> request) {
        return erpOutBiz.JK0017(request);

    }


    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0018)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0018Response> JK0018(@RequestBody BaseRequest<JK0018Request> request) {
        return erpOutBiz.JK0018(request);

    }


    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0019)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0019Response> JK0019(@RequestBody BaseRequest<JK0019Request> request) {
        return erpOutBiz.JK0019(request);

    }


    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0022)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0022Response> JK0022(@RequestBody BaseRequest<JK0022Request> request) {
        return erpOutBiz.JK0022(request);
    }


    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0024)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0024Response> JK0024(@RequestBody BaseRequest<JK0024Request> request) {
        return erpOutBiz.JK0024(request);
    }


    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0027)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0027Response> JK0027(@RequestBody BaseRequest<JK0027Request> request) {
        return erpOutBiz.JK0027(request);
    }


    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0028)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0028Response> JK0028(@RequestBody BaseRequest<JK0028Request> request) {
        return erpOutBiz.JK0028(request);
    }


    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0030)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0030Response> JK0030(@RequestBody BaseRequest<JK0030Request> request) {
        return erpOutBiz.JK0030(request);
    }

    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0031)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0031Response> JK0031(@RequestBody BaseRequest<JK0031Request> request) {
        return erpOutBiz.JK0031(request);
    }

    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0032)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0032Response> JK0032(@RequestBody BaseRequest<JK0032Request> request) {
        return erpOutBiz.JK0032(request);
    }

    @Anonymous
    @PostMapping(Urls.ErpOutRest.oaTest)
    public void oaTest(@RequestBody BaseRequest<String> request) {
         erpOutBiz.oaTest(request.getReqData());
    }

    @Override
    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0033)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0033Response> JK0033(@RequestBody BaseRequest<JK0033Request> request) {
        return erpOutBiz.JK0033(request);
    }


    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0034)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0034Response> JK0034(@RequestBody BaseRequest<JK0034Request> request) {
        return erpOutBiz.JK0034(request);
    }

    @Anonymous
    @PostMapping(Urls.ErpOutRest.JK0035)
    @InterfaceAnnotation(caller = "PT", callee = "ERP")
    public BaseResponse<JK0035Response> JK0035(@RequestBody BaseRequest<JK0035Request> request) {
        return erpOutBiz.JK0035(request);
    }
}
