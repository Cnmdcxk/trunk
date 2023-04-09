package netplus.provider.service.controller;

import netplus.component.entity.auth.Anonymous;
import netplus.provider.api.Urls;
import netplus.provider.api.request.WorkbenchRequest;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.provider.service.biz.WorkbenchBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sy on 2021/1/7.
 */
@RestController
public class WorkbenchController {
    @Autowired
    private WorkbenchBiz workbenchBiz;

    @Anonymous
    @PostMapping(Urls.Workbench.getSupplier)
    public Tbdu01Vo getSupplier(@RequestBody WorkbenchRequest workbenchRequest){
        return new Tbdu01Vo();
    }
}
