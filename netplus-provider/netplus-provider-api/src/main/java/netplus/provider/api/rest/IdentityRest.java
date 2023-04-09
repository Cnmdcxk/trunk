package netplus.provider.api.rest;


import netplus.component.entity.auth.IPrivilege;
import netplus.component.entity.iface.BaseResponse;
import netplus.component.entity.response.ApiResponse;
import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.joint.erp.api.response.out.JK0035.Data;
import netplus.joint.erp.api.response.out.JK0035.JK0035Response;
import netplus.provider.api.Urls;
import netplus.provider.api.request.*;
import netplus.provider.api.vo.LoginResult;
import netplus.provider.api.vo.LoginUserBean;
import netplus.provider.api.vo.Tbdu01Vo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "netplus-provider-service",
//        url = "${service.netplus-provider-service}",
        url = "http://127.0.0.1:20007",

        contextId = "IdentityRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface IdentityRest extends IPrivilege {

    @PostMapping(Urls.getCurrentUser)
    LoginUserBean getCurrentUser();

    @PostMapping(Urls.getUserPrivileges)
    List<String> getUserPrivileges();

    @PostMapping(Urls.doLogin)
    LoginResult doLogin(LoginRequest loginRequest);

    @PostMapping(Urls.updateUserPwd)
    ApiResponse updateUserPwd(UpdateUserPwdRequest request);

    @PostMapping(Urls.retrievePasswordSendSmsCode)
    ApiResponse retrievePasswordSendSmsCode(RetrievePwdSmsCodeRequest request);


    @PostMapping(Urls.retrieve)
    ApiResponse retrievePasswordCheckSmsCode(RetrievePwdSmsCodeRequest request);


    @PostMapping(Urls.updPassword)
    ApiResponse retrievePasswordUpdPassword(RetrievePwdUpdateRequest request);


    @PostMapping(Urls.getUserByUserNo)
    Tbdu01Vo getUserByUserNo(GetUserInfoRequest request);

    @PostMapping(Urls.updateSupplierBizContact)
    ApiResponse updateSupplierBizContact(@RequestBody UpdateBizContactRequest request);

    @PostMapping(Urls.getSupplierBizContact)
    Map<String,String> getSupplierBizContact(@RequestBody GetSupplierBizContactRequest request);

    @PostMapping(Urls.getProviderTokenInfo)
    LoginResult getProviderTokenInfo (@RequestBody GetProviderTokenInfoRequest request);

    @PostMapping(Urls.chatLogin)
    BaseResponse<JK0035Response> chatLogin(@RequestBody LoginRequest request);
}
