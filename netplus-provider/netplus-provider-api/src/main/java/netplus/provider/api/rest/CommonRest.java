package netplus.provider.api.rest;


import netplus.component.entity.data.PageBean;
import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.provider.api.Urls;
import netplus.provider.api.pojo.ygmalluser.Tbmqq439;
import netplus.provider.api.request.GetAddrListRequest;
import netplus.provider.api.request.GetDialogInfoRequest;
import netplus.provider.api.vo.Tbdu01Vo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "netplus-provider-service",
        url = "${service.netplus-provider-service}",
        contextId = "CommonRest",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class}
)
public interface CommonRest {
    @PostMapping(Urls.Common.getAddrList)
    List<Tbmqq439> getAddrList (@RequestBody GetAddrListRequest request);


    @PostMapping(Urls.Common.getUserPageList)
    PageBean<Tbdu01Vo> getUserPageList(@RequestBody GetDialogInfoRequest request);


    @PostMapping(Urls.Common.getRoleListByUser)
    List<String> getRoleListByUser();
}
