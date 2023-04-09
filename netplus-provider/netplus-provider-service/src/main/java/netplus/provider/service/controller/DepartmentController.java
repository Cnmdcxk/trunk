package netplus.provider.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.provider.api.Urls;
import netplus.provider.api.pojo.ygmalluser.Department;
import netplus.provider.api.request.department.GetDepartmentRequest;
import netplus.provider.api.rest.DepartmentRest;
import netplus.provider.service.biz.DepartmentBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController extends BasedController implements DepartmentRest {

    @Autowired
    private DepartmentBiz departmentBiz;

    @Anonymous
    @PostMapping(Urls.syncDepartment)
    public void syncDepartment () {
        departmentBiz.syncDepartment();
    }


    @Anonymous
    @PostMapping(Urls.getDepartment)
    public Department getDepartment (@RequestBody GetDepartmentRequest request) {
        return departmentBiz.getDepartment(request);
    }
}
