package netplus.joint.erp.api.rest;


import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.BaseResponse;
import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
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
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "netplus-joint-erp-out-service",
//        url = "${service.netplus-joint-erp-out-service}",
        url = "http://localhost:20034",
        contextId = "ErpOutRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface ErpOutRest {

    @PostMapping(Urls.ErpOutRest.JK0001)
    BaseResponse<JK0001Response> JK0001(@RequestBody BaseRequest<JK0001Request> request);

    @PostMapping(Urls.ErpOutRest.JK0002)
    BaseResponse<JK0002Response> JK0002(@RequestBody BaseRequest<JK0002Request> request);

    @PostMapping(Urls.ErpOutRest.JK0003)
    BaseResponse<JK0003Response> JK0003(@RequestBody BaseRequest<JK0003Request> request);
    @PostMapping(Urls.ErpOutRest.JK0004)
    BaseResponse<JK0004Response> JK0004(@RequestBody BaseRequest<JK0004Request> request);
    @PostMapping(Urls.ErpOutRest.JK0005)
    BaseResponse<JK0005Response> JK0005(@RequestBody BaseRequest<JK0005Request> request);

    @PostMapping(Urls.ErpOutRest.JK0006)
    BaseResponse<JK0006Response> JK0006(@RequestBody BaseRequest<JK0006Request> request);

    @PostMapping(Urls.ErpOutRest.JK0007)
    BaseResponse<JK0007Response> JK0007(@RequestBody BaseRequest<JK0007Request> request);


    @PostMapping(Urls.ErpOutRest.JK0009)
    BaseResponse<JK0009Response> JK0009(@RequestBody BaseRequest<JK0009Request> request);

    @PostMapping(Urls.ErpOutRest.JK0010)
     BaseResponse<JK0010Response> JK0010(@RequestBody BaseRequest<JK0010Request> request);

     @PostMapping(Urls.ErpOutRest.JK0011)
     BaseResponse<JK0011Response> JK0011(@RequestBody BaseRequest<JK0011Request> request);


    @PostMapping(Urls.ErpOutRest.JK0012)
    BaseResponse<JK0012Response> JK0012(@RequestBody BaseRequest<JK0012Request> request);

    @PostMapping(Urls.ErpOutRest.JK0013)
    BaseResponse<JK0013Response> JK0013(@RequestBody BaseRequest<JK0013Request> request);

    @PostMapping(Urls.ErpOutRest.JK0015)
    BaseResponse<JK0015Response> JK0015(@RequestBody BaseRequest<JK0015Request> request);


    @PostMapping(Urls.ErpOutRest.JK0016)
    BaseResponse<JK0016Response> JK0016(@RequestBody BaseRequest<JK0016Request> request);


    @PostMapping(Urls.ErpOutRest.JK0017)
    BaseResponse<JK0017Response> JK0017(@RequestBody BaseRequest<JK0017Request> request);


    @PostMapping(Urls.ErpOutRest.JK0018)
    public BaseResponse<JK0018Response> JK0018(@RequestBody BaseRequest<JK0018Request> request);

    @PostMapping(Urls.ErpOutRest.JK0019)
    public BaseResponse<JK0019Response> JK0019(@RequestBody BaseRequest<JK0019Request> request);

    @PostMapping(Urls.ErpOutRest.JK0022)
    BaseResponse<JK0022Response> JK0022(@RequestBody BaseRequest<JK0022Request> request);

    @PostMapping(Urls.ErpOutRest.JK0024)
    BaseResponse<JK0024Response> JK0024(@RequestBody BaseRequest<JK0024Request> request);

    @PostMapping(Urls.ErpOutRest.JK0027)
    BaseResponse<JK0027Response> JK0027(@RequestBody BaseRequest<JK0027Request> request);

    @PostMapping(Urls.ErpOutRest.JK0028)
    BaseResponse<JK0028Response> JK0028(@RequestBody BaseRequest<JK0028Request> request);

    @PostMapping(Urls.ErpOutRest.JK0030)
    BaseResponse<JK0030Response> JK0030(@RequestBody BaseRequest<JK0030Request> request);

    @PostMapping(Urls.ErpOutRest.JK0031)
    BaseResponse<JK0031Response> JK0031(@RequestBody BaseRequest<JK0031Request> request);

    @PostMapping(Urls.ErpOutRest.JK0032)
    BaseResponse<JK0032Response> JK0032(@RequestBody BaseRequest<JK0032Request> request);

    @PostMapping(Urls.ErpOutRest.JK0033)
    BaseResponse<JK0033Response> JK0033(@RequestBody BaseRequest<JK0033Request> request);


    @PostMapping(Urls.ErpOutRest.JK0034)
    BaseResponse<JK0034Response> JK0034(@RequestBody BaseRequest<JK0034Request> request) ;

    @PostMapping(Urls.ErpOutRest.JK0035)
    BaseResponse<JK0035Response> JK0035(@RequestBody BaseRequest<JK0035Request> request) ;
}
