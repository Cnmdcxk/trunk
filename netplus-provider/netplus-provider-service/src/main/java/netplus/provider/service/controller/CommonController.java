package netplus.provider.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.data.PageBean;
import netplus.provider.api.pojo.ygmalluser.Tbmqq439;
import netplus.provider.api.request.GetAddrListRequest;
import netplus.provider.api.request.GetDialogInfoRequest;
import netplus.provider.api.rest.CommonRest;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.provider.service.biz.CommonBiz;
import netplus.provider.service.biz.ProviderBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommonController extends BasedController implements CommonRest {

    @Autowired
    CommonBiz commonBiz;

    @Autowired
    ProviderBiz providerBiz;


    /**
     * 取收货地址数据（tbdu05）
     *
     * @param request
     * @return
     */
    @Anonymous
    public List<Tbmqq439> getAddrList (GetAddrListRequest request) {
        return commonBiz.getAddrList(request);
    }


    /**取用户数据（tbdu01）
     *
     * add by wangyx
     * @return
     */
    @Override
    public PageBean<Tbdu01Vo> getUserPageList(@RequestBody GetDialogInfoRequest request){
        return commonBiz.getUserPageList(request);
    }


    @Override
    public List<String> getRoleListByUser() {

        return commonBiz.getRoleListByUser();
    }







}
