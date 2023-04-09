package netplus.provider.service.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.BaseResponse;
import netplus.component.entity.response.ApiResponse;
import netplus.joint.erp.api.request.out.JK0030Request;
import netplus.joint.erp.api.response.out.JK0030Response;
import netplus.joint.erp.api.response.out.JK0035.Data;
import netplus.joint.erp.api.response.out.JK0035.JK0035Response;
import netplus.joint.erp.api.rest.ErpOutRest;
import netplus.provider.api.Urls;
import netplus.provider.api.enums.EmpTypeEnums;
import netplus.provider.api.enums.EnumLoginCode;
import netplus.provider.api.enums.UserTypeEnums;
import netplus.provider.api.pojo.ygmalluser.Tbdu01;
import netplus.provider.api.request.*;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginResult;
import netplus.provider.api.vo.LoginUserBean;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.provider.service.biz.IdentityBiz;
import netplus.provider.service.biz.SysLogBiz;
import netplus.provider.service.dao.Tbdu01Mapper;
import netplus.provider.service.dao.Tbmqq423Mapper;
import netplus.utils.object.ObjectUtil;
import netplus.utils.secure.JwtUtil;
import netplus.utils.uuid.UuidUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IdentityController extends BasedController implements IdentityRest {

    @Autowired
    private Tbdu01Mapper tbdu01Mapper;

    @Autowired
    Tbmqq423Mapper tbmqq423Mapper;

    @Autowired
    private IdentityBiz identityBiz;


    @Value("${sign.key}")
    private String signKey;

    @Autowired
    private ErpOutRest erpOutRest;

    @Autowired
    private SysLogBiz sysLogBiz;

    /*
     * 获取当前登录用户信息
     * */
    public LoginUserBean getCurrentUser() {

        Tbdu01 tbdu01 = tbdu01Mapper.selectByPrimaryKey(getUserId());
        if (tbdu01 == null) {
            throw new NetPlusException(String.format("用户信息: %s不存在", getUserId()));
        }

        LoginUserBean loginUserBean = new LoginUserBean();
        loginUserBean.setCompanyCode(tbdu01.getCompno());
        loginUserBean.setCompanyName(tbdu01.getCompname());
        loginUserBean.setUserCode(tbdu01.getUserno());
        loginUserBean.setUsername(tbdu01.getName());
        loginUserBean.setMobile(tbdu01.getPhone());
        loginUserBean.setDeptNo(tbdu01.getDepno());
        loginUserBean.setDeptName(tbdu01.getDepname());
        loginUserBean.setRole(tbdu01.getUsertype());
        loginUserBean.setDeptNum(tbdu01.getDepnum());
        loginUserBean.setCompanyPk(tbdu01.getComppk());
        loginUserBean.setBizContact(tbdu01.getBizcontact());
        loginUserBean.setBizContactPhone(tbdu01.getBizcontactphone());

        return loginUserBean;
    }

    public List<String> getUserPrivileges() {

        LoginUserBean loginUserBean = getCurrentUser();

        Map<String, Object> map = new HashMap();
        map.put("userno", loginUserBean.getUserCode());
        return tbmqq423Mapper.getUserPrivileges(map);
    }

    @Anonymous
    public LoginResult doLogin(@RequestBody LoginRequest loginRequest) {
        LoginResult loginResult = new LoginResult();
        String method = loginRequest.getLoginMethod();
        Tbdu01Vo tbdu01Vo = null;

        // 永刚用户登录
        if (method.equals("U")) {

            tbdu01Vo = tbdu01Mapper.getUserByUserNoAndUserType(loginRequest.getUsername(), "B");

            if (ObjectUtil.isEmpty(tbdu01Vo)) {
                loginResult.setCode(EnumLoginCode.NotExists.getValue());
                loginResult.setMsg(EnumLoginCode.NotExists.getDesc());
            }else{

                identityBiz.unifiedAuth(loginRequest.getPassword(), loginRequest.getUsername());

                String authToken = JwtUtil.sign(tbdu01Vo.getUserno(), tbdu01Vo.getUserno(), loginRequest.getSource(), signKey);
                loginResult.setCode(EnumLoginCode.OK.getValue());
                loginResult.setAuthToken(authToken);
            }

        // 供应商登录
        } else if (method.equals("M")) {


            String providerNo = identityBiz.providerAuth(loginRequest.getPassword(), loginRequest.getUsername());
            tbdu01Vo = tbdu01Mapper.getUserByUserNoAndUserType("M" + providerNo , "S");

            if (ObjectUtil.isEmpty(tbdu01Vo)) {
                loginResult.setCode(EnumLoginCode.ProviderNotSync.getValue());
                loginResult.setMsg(EnumLoginCode.ProviderNotSync.getDesc());
            } else {

                String authToken = JwtUtil.sign(tbdu01Vo.getCompno(), tbdu01Vo.getUserno(), loginRequest.getSource(), signKey);
                loginResult.setCode(EnumLoginCode.OK.getValue());
                loginResult.setAuthToken(authToken);
            }

        }

        //客商系统移动端对接供应商登录
        else if (method.equals("M2")) {

            tbdu01Vo = tbdu01Mapper.getUserByUserNoAndUserType("M" + loginRequest.getUsername() , "S");

            if (ObjectUtil.isEmpty(tbdu01Vo)) {
                loginResult.setCode(EnumLoginCode.ProviderNotSync.getValue());
                loginResult.setMsg(EnumLoginCode.ProviderNotSync.getDesc());
            } else {

                String authToken = JwtUtil.sign(tbdu01Vo.getCompno(), tbdu01Vo.getUserno(), loginRequest.getSource(), signKey);
                loginResult.setCode(EnumLoginCode.OK.getValue());
                loginResult.setAuthToken(authToken);
            }

        }
        //模拟登入
        else if (method.equals("S")) {


            identityBiz.unifiedAuth(loginRequest.getPassword(), loginRequest.getUsername());
            Tbdu01 tbdu01 = tbdu01Mapper.selectByPrimaryKey(loginRequest.getMockUsername());

            if (ObjectUtil.isEmpty(tbdu01)) {
                loginResult.setCode(EnumLoginCode.NotExists.getValue());
                loginResult.setMsg(EnumLoginCode.NotExists.getDesc());
            }else{


                tbdu01Vo=transformVo(tbdu01);

                String authToken;
                if (tbdu01.getUsertype().equals(UserTypeEnums.B.getCode())) {
                    authToken = JwtUtil.sign(tbdu01.getUserno(), tbdu01.getUserno(), loginRequest.getSource(), signKey);
                }else{
                    authToken = JwtUtil.sign(tbdu01.getCompno(), tbdu01.getUserno(), loginRequest.getSource(), signKey);
                }

                loginResult.setCode(EnumLoginCode.OK.getValue());
                loginResult.setAuthToken(authToken);
            }

        //系统对接登录
        } else if (method.equals("Q")) {

            Tbdu01 tbdu01 = tbdu01Mapper.selectByPrimaryKey(loginRequest.getUsername());

            if (ObjectUtil.isEmpty(tbdu01)) {
                loginResult.setCode(EnumLoginCode.NotExists.getValue());
                loginResult.setMsg(EnumLoginCode.NotExists.getDesc());
            }else{

                tbdu01Vo=transformVo(tbdu01);

                String authToken;
                if (tbdu01.getUsertype().equals(UserTypeEnums.B.getCode())) {
                    authToken = JwtUtil.sign(tbdu01.getUserno(), tbdu01.getUserno(), loginRequest.getSource(), signKey);
                }else{
                    authToken = JwtUtil.sign(tbdu01.getCompno(), tbdu01.getUserno(), loginRequest.getSource(), signKey);
                }

                loginResult.setCode(EnumLoginCode.OK.getValue());
                loginResult.setAuthToken(authToken);
            }

        } else {
            loginResult.setCode(EnumLoginCode.LoginMethodErr.getValue());
            loginResult.setMsg(EnumLoginCode.LoginMethodErr.getDesc());
        }

        if(!ObjectUtil.isEmpty(tbdu01Vo) && EmpTypeEnums.two.getCode().equals(tbdu01Vo.getEmptype())){
            loginResult.setCode(EnumLoginCode.ConsigneeDisallowLogin.getValue());
            loginResult.setMsg(EnumLoginCode.ConsigneeDisallowLogin.getDesc());
            loginResult.setAuthToken(null);
        }

        return loginResult;
    }

    private Tbdu01Vo transformVo(Tbdu01 tbdu01) {
        Tbdu01Vo tbdu01Vo = new Tbdu01Vo();
        BeanUtils.copyProperties(tbdu01, tbdu01Vo);
        return tbdu01Vo;
    }

    @Override
    public ApiResponse updateUserPwd(@RequestBody UpdateUserPwdRequest request) {
        return identityBiz.updateUserPwd(request);
    }

    @Anonymous
    @Override
    public ApiResponse retrievePasswordSendSmsCode(@RequestBody RetrievePwdSmsCodeRequest request) {
        return identityBiz.retrievePasswordSendSmsCode(request);
    }

    @Anonymous
    @Override
    public ApiResponse retrievePasswordCheckSmsCode(@RequestBody RetrievePwdSmsCodeRequest request) {
        return identityBiz.retrievePasswordCheckSmsCode(request);
    }

    @Anonymous
    @Override
    public ApiResponse retrievePasswordUpdPassword(@RequestBody RetrievePwdUpdateRequest request) {
        return identityBiz.retrievePasswordUpdPassword(request);
    }

    @Anonymous
    @Override
    public Tbdu01Vo getUserByUserNo(@RequestBody GetUserInfoRequest request) {
        return identityBiz.getUserByUserNo(request.getUserNo());
    }

    @Override
    public ApiResponse updateSupplierBizContact(@RequestBody UpdateBizContactRequest request){
        return identityBiz.updateSupplierBizContact(getUserId(),request);
    }

    @Override
    public Map<String,String> getSupplierBizContact(@RequestBody GetSupplierBizContactRequest request){
        return identityBiz.getSupplierBizContact(request);
    }

    @Override
    @Anonymous
    public LoginResult getProviderTokenInfo (@RequestBody GetProviderTokenInfoRequest request) {

        JK0030Request jk0030Request = new JK0030Request();
        jk0030Request.setTicket(request.getTicket());

        BaseRequest<JK0030Request> baseRequest = new BaseRequest();
        baseRequest.setReqId(UuidUtil.getUuid());
        baseRequest.setReqTime(String.valueOf(new Date().getTime()));
        baseRequest.setReqData(jk0030Request);
        BaseResponse<JK0030Response> resp = erpOutRest.JK0030(baseRequest);

        if (resp.getStatus().equals("0")) {
            throw new NetPlusException(resp.getMessage());
        }

        JK0030Response jk0030Response = resp.getRespData();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSource(request.getSourceFrom());
        loginRequest.setUsername(jk0030Response.getKeShi());
        loginRequest.setLoginMethod("M2");

        return doLogin(loginRequest);


    }

    @Anonymous
    @PostMapping(Urls.chatLogin)
    public BaseResponse<JK0035Response> chatLogin(@RequestBody  LoginRequest request){
        return identityBiz.chatLogin(request);
    }


}
