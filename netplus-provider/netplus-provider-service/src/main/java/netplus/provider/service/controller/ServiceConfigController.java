package netplus.provider.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.response.ApiResponse;
import netplus.provider.api.Urls;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.request.AddServiceConfigRequest;
import netplus.provider.api.request.ServiceConfigRequest;
import netplus.provider.api.rest.ServiceConfigRest;
import netplus.provider.api.vo.Tbmqq461Vo;
import netplus.provider.service.biz.ServiceConfigBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 业务配置
@RestController
public class ServiceConfigController extends BasedController implements ServiceConfigRest {

    @Autowired
    ServiceConfigBiz serviceConfigBiz;

    @PostMapping(Urls.ServiceData.getListService)
    public PageBean<Tbmqq461Vo> getListService (@RequestBody ServiceConfigRequest request) {

        return serviceConfigBiz.getListService(request);
    }

    @PostMapping(Urls.ServiceData.addServiceConfig)
    public ApiResponse addServiceConfig (@RequestBody AddServiceConfigRequest request) {

        return serviceConfigBiz.addServiceConfig(request);
    }

    @PostMapping(Urls.ServiceData.updateServiceConfig)
    public ApiResponse updateServiceConfig (@RequestBody AddServiceConfigRequest request) {
        return serviceConfigBiz.updateServiceConfig(request);
    }

    @PostMapping(Urls.ServiceData.deleteServiceConfig)
    public ApiResponse deleteServiceConfig (@RequestBody AddServiceConfigRequest request) {
        return serviceConfigBiz.deleteServiceConfig(request);
    }

    @Override
    @Anonymous
    @PostMapping(Urls.ServiceData.getServiceConfigByCode)
    public Tbmqq461 getServiceConfigByCode (@RequestBody String code) {
        return serviceConfigBiz.getServiceConfigByCode(code);
    }
}
