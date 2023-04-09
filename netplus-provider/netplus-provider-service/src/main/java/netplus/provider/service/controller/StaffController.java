package netplus.provider.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.data.PageBean;
import netplus.provider.api.Urls;
import netplus.provider.api.request.*;
import netplus.provider.api.rest.StaffRest;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.provider.api.vo.Tbmqq420Vo;
import netplus.provider.service.biz.StaffBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StaffController extends BasedController implements StaffRest {

    @Autowired
    StaffBiz staffBiz;

    @PostMapping(Urls.Staff.getStaffList)
    public PageBean<Tbdu01Vo> getList (@RequestBody GetStaffListRequest request) {

        return staffBiz.getList(request);
    }
    @PostMapping(Urls.Staff.updateRole)
    public void updateRole (@RequestBody UpdateStaffRoleRequest request) {
        staffBiz.updateRole(request);
    }


    @PostMapping(Urls.Staff.updateActive)
    public void updateActive (@RequestBody UpdateStaffStatusRequest request) {
        staffBiz.updateActive(request);
    }


    @PostMapping(Urls.Staff.exportStaff)
    public String exportStaff(@RequestBody GetStaffListRequest request) throws Exception {
        return staffBiz.exportStaff(request);
    }

    @PostMapping(Urls.Staff.getUserOwnRoleByUserNo)
    public List<Tbmqq420Vo> getUserOwnRoleByUserNo (@RequestBody GetUserOwnRoleByUserNoRequest request) {
        return staffBiz.getUserOwnRoleByUserNo(request);
    }


    @PostMapping(Urls.Staff.getConsignee)
    public List<Tbdu01Vo> getConsignee (@RequestBody GetConsigneeRequest request) {
        return staffBiz.getConsignee(request);
    }


    @Anonymous
    @PostMapping(Urls.Staff.getConsigneeByUserNo)
    public Tbdu01Vo getConsigneeByUserNo (@RequestBody GetConsigneeRequest request) {
        return staffBiz.getConsigneeByUserNo(request);
    }

    @PostMapping(Urls.Staff.getUserRoleList)
    public List<String> getUserRoleList (@RequestBody GetUserRoleListRequest request) {
        return staffBiz.getUserRoleList(request);
    }

}
