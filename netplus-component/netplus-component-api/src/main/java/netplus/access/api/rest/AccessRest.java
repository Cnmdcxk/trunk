package netplus.access.api.rest;


import netplus.component.entity.auth.ApplicationVo;
import netplus.component.entity.auth.IAccess;
import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "netplus-component-service",
        url="${service.netplus-component-service}",
        contextId="AccessRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface AccessRest extends IAccess {

    @PostMapping(AccessConstants.selectAccessTokenByDomain)
    String selectAccessTokenByDomain(@RequestParam("domain") String domain);

    @PostMapping(AccessConstants.selectByAccessToken)
    ApplicationVo selectByAccessToken(@RequestParam("accessToken") String accessToken);

}
