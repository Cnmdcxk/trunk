package netplus.provider.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.response.ApiResponse;
import netplus.provider.api.Urls;
import netplus.provider.api.pojo.ygmalluser.Tbmqq420;
import netplus.provider.api.request.GetRolePrivilegeListRequest;
import netplus.provider.api.request.RolemanagerRequest;
import netplus.provider.api.request.SaveRolePrivilegeRequest;
import netplus.provider.api.vo.Tbmqq420Vo;
import netplus.provider.api.vo.Tbmqq423Parent;
import netplus.provider.api.vo.Tbmqq423Vo;
import netplus.provider.service.biz.RoleBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//角色管理
@RestController
public class RoleController extends BasedController {

    @Autowired
    RoleBiz roleBiz;

    @PostMapping(Urls.RolemanagerData.getList)
    public PageBean<Tbmqq420Vo> getList (@RequestBody RolemanagerRequest request) {

        return roleBiz.getList(request);
    }

    @PostMapping(Urls.RolemanagerData.delete)
    public void delete (@RequestBody RolemanagerRequest request) {
         roleBiz.delete(request);
    }

    @PostMapping(Urls.RolemanagerData.islist)
    public boolean islist (@RequestBody RolemanagerRequest request) {
        return roleBiz.islist(request);
    }

    @PostMapping(Urls.RolemanagerData.addrole)
    public ApiResponse addRole (@RequestBody RolemanagerRequest request) {
        return roleBiz.addRole(request);
    }

    @PostMapping(Urls.RolemanagerData.getListPermissions)
    public List<Tbmqq423Parent> getListPermissions () {
        return roleBiz.getListPermissions();
    }

    @PostMapping(Urls.RolemanagerData.updaterole)
    public ApiResponse updateRole (@RequestBody RolemanagerRequest request) {
        return roleBiz.updateRole(request);
    }

    @PostMapping(Urls.RolemanagerData.getRolePrivilegeList)
    public List<Tbmqq423Vo> getRolePrivilegeList (@RequestBody GetRolePrivilegeListRequest request) {
        return roleBiz.getRolePrivilegeList(request);
    }

    @PostMapping(Urls.RolemanagerData.saveRolePrivilege)
    public void saveRolePrivilege (@RequestBody SaveRolePrivilegeRequest request) {
        roleBiz.saveRolePrivilege(request);
    }

    @PostMapping(Urls.RolemanagerData.getRoleOwnPrivileges)
    public List<Tbmqq423Vo> getRoleOwnPrivileges (@RequestBody GetRolePrivilegeListRequest request) {
        return roleBiz.getRoleOwnPrivileges(request);
    }

}
