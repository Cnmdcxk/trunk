package netplus.provider.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.provider.api.Urls;
import netplus.provider.api.request.GetSupplierNoRequest;
import netplus.provider.api.request.SyncProviderRequest;
import netplus.provider.api.rest.ProvideRest;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.provider.service.biz.ProviderBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ProviderController extends BasedController implements ProvideRest {

    @Autowired
    private ProviderBiz providerBiz;

    @Anonymous
    public void syncProvider (@RequestBody SyncProviderRequest request) {
        providerBiz.syncProvider(request);
    }


    @Anonymous
    @PostMapping(Urls.getYgUser)
    public void getYgUser() {
        providerBiz.getYgUser();
    }


    @Anonymous
    @PostMapping(Urls.getSupplierNo)
    public Tbdu01Vo getSupplierNo(@RequestBody GetSupplierNoRequest request) {
        return providerBiz.getSupplierNo(request);
    }


    @Anonymous
    @PostMapping(Urls.getSupplierByName)
    public Tbdu01Vo getSupplierByName(@RequestBody GetSupplierNoRequest request) {
        return providerBiz.getSupplierByName(request);
    }


    @Anonymous
    @PostMapping(Urls.getSupplierNoList)
    public List<Tbdu01Vo> getSupplierNoList(@RequestBody List<String> request) {
        return providerBiz.getSupplierNoList(request);
    }





}
