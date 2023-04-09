package netplus.provider.service.controller;


import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.response.ApiResponse;
import netplus.provider.api.Urls;
import netplus.provider.api.request.GetMenuListRequest;
import netplus.provider.api.request.permissionsRequest;
import netplus.provider.api.vo.MenuVo;
import netplus.provider.api.vo.Tbmqq423Vo;
import netplus.provider.service.biz.PermissionsBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PermissionsController {

    @Autowired
    PermissionsBiz permissionsBiz;

    @Anonymous
    @PostMapping(Urls.permissions.getAllList)
    public PageBean<Tbmqq423Vo> getAllList(@RequestBody permissionsRequest request){
        return permissionsBiz.getList(request);
    }

    @Anonymous
    @PostMapping(Urls.permissions.addPrivilege)
    public ApiResponse addPrivilege(@RequestBody permissionsRequest request){
        return permissionsBiz.addPrivilege(request);
    }

    @Anonymous
    @PostMapping(Urls.permissions.modifyPrivilege)
    public ApiResponse modifyPrivilege(@RequestBody permissionsRequest request){
        return permissionsBiz.modifyPrivilege(request);
    }

    @Anonymous
    @PostMapping(Urls.permissions.getByCodeList)
    public PageBean<Tbmqq423Vo> getByCodeList(@RequestBody permissionsRequest request){
        return permissionsBiz.getPrivilegeTypeList(request);
    }

    @PostMapping(Urls.permissions.getUserMenuList)
    public List<MenuVo> getUserMenuList (@RequestBody GetMenuListRequest request) {
        return permissionsBiz.getUserMenuList(request);
    }
}
