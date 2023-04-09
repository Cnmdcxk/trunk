package netplus.provider.service.biz;

import netplus.component.entity.data.PageBean;
import netplus.provider.api.pojo.ygmalluser.Tbmqq439;
import netplus.provider.api.request.GetAddrListRequest;
import netplus.provider.api.request.GetDialogInfoRequest;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.provider.service.dao.Tbdu01Mapper;
import netplus.provider.service.dao.Tbmqq422Mapper;
import netplus.provider.service.dao.Tbmqq439Mapper;
import netplus.utils.object.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommonBiz {

    @Autowired
    IdentityRest identityRest;

    @Autowired
    Tbdu01Mapper tbdu01Mapper;

    @Autowired
    Tbmqq439Mapper tbmqq439Mapper;

    @Autowired
    Tbmqq422Mapper tbmqq422Mapper;


    /**
     * 取收货地址数据（tbdu05）
     *
     * @param request
     * @return
     */

    public List<Tbmqq439> getAddrList(GetAddrListRequest request) {

        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);

        return tbmqq439Mapper.getList(mapParam);
    }



    /**
     * 取用户数据（tbdu01）
     *
     * @param request
     * @return
     */
    public PageBean<Tbdu01Vo> getUserPageList(GetDialogInfoRequest request){
        Map<String , Object> map = ObjectUtil.transBeanToMap(request);
        List<Tbdu01Vo> tbdu01VoList = new ArrayList();
        int totalCount = 0;

        PageBean<Tbdu01Vo> pageBean = new PageBean();
        pageBean.setItems(tbdu01VoList);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }



    public List<String>getRoleListByUser(){
        Map<String, Object> mapParam =new HashMap<>();
        LoginUserBean loginUserBean=identityRest.getCurrentUser();
        mapParam.put("userno",loginUserBean.getUserCode());
        return tbmqq422Mapper.selectRoleList(mapParam);
    }












}
