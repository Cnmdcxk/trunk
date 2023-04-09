package netplus.portal.web.controller;

import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.consts.SysConstant;
import netplus.component.entity.iface.BaseResponse;
import netplus.joint.erp.api.response.out.JK0035.Data;
import netplus.joint.erp.api.response.out.JK0035.JK0035Response;
import netplus.provider.api.enums.EnumLoginCode;
import netplus.provider.api.request.LoginRequest;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginResult;
import netplus.provider.api.vo.LoginUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class IndexController extends BasedController {

    @Autowired
    IdentityRest identityRest;

    @Anonymous
    @GetMapping("/")
    public String index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

        String userId = getUserId();
        if (!StringUtils.isEmpty(userId)) {
            LoginUserBean loginUserBean = identityRest.getCurrentUser();
            if (loginUserBean.getRole().equals("S")) {
                return String.format("redirect:https://%s%s", request.getServerName(), "/provider/workbench");
            }
        }

        String appId = request.getParameter("appid");
        String ticket = request.getParameter("ticket");

        if(!StringUtils.isEmpty(appId)&&!StringUtils.isEmpty(ticket)){
            //解析ticket
            LoginRequest loginRequest =new LoginRequest();
            loginRequest.setTicket(ticket);
            loginRequest.setAppId(appId);
            BaseResponse<JK0035Response> jk0035ResponseBaseResponse = identityRest.chatLogin(loginRequest);
            JK0035Response jk0035Response = jk0035ResponseBaseResponse .getRespData();
            if(jk0035ResponseBaseResponse.getStatus().equals("1")) {
                loginRequest.setLoginMethod("Q");
                loginRequest.setUsername(jk0035Response.getData().getAccount());
                //登录
                LoginResult loginResult = identityRest.doLogin(loginRequest);
                if (EnumLoginCode.OK.getValue().equals(loginResult.getCode())) {
                    Cookie authCookie = new Cookie(SysConstant.AuthToken, loginResult.getAuthToken());
                    modelMap.put("UserID",jk0035Response.getData().getAccount());
                    modelMap.put("ticket",ticket);
                    modelMap.put("appId" ,appId);
                    modelMap.put("role" , jk0035Response.getData().getRole());
                    authCookie.setHttpOnly(true);
                    authCookie.setPath("/");
                    response.addCookie(authCookie);
                }
            }
        }
        return "index";
    }

}
