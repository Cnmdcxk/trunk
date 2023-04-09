package netplus.access.oldservice.controller;

import io.swagger.annotations.Api;
import netplus.access.api.rest.AccessConstants;
import netplus.access.api.rest.AccessRest;
import netplus.access.oldservice.biz.AccessBiz;
import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.AccessAnonymous;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.auth.ApplicationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="Access接口文档")
@RestController
public class AccessController extends BasedController implements AccessRest {

    @Autowired
    private AccessBiz accessBiz;


    @Anonymous
    @AccessAnonymous
    @PostMapping(AccessConstants.selectAccessTokenByDomain)
    public String selectAccessTokenByDomain(@RequestParam("domain") String domain){
        return accessBiz.selectAccessTokenByDomain(domain);
    }


    @Anonymous
    @AccessAnonymous
    @PostMapping(AccessConstants.selectByAccessToken)
    public ApplicationVo selectByAccessToken(@RequestParam("accessToken") String accessToken){
        return accessBiz.selectByAccessToken(accessToken);
    }

}
