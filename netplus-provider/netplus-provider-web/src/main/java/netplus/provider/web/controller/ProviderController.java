package netplus.provider.web.controller;


import netplus.cache.api.rest.CacheRest;
import netplus.component.authbase.BasedController;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.auth.PreAuth;
import netplus.component.entity.consts.SysConstant;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.response.ApiResponse;
import netplus.provider.api.enums.EnumLoginCode;
import netplus.provider.api.request.CheckPhoneRequest;
import netplus.provider.api.request.LoginRequest;
import netplus.provider.api.request.MockLoginRequest;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginResult;
import netplus.provider.api.vo.LoginUserBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

@Controller
@RequestMapping("/provider")
public class ProviderController extends BasedController {


    protected Log logger = LogFactory.getLog(ProviderController.class);

    @Autowired
    IdentityRest identityRest;

    @Autowired
    private CacheRest cacheRest;

    @Anonymous
    @GetMapping("/login")
    public String login(HttpServletRequest request, ModelMap modelMap) {

        String returnUrl = request.getParameter("returnUrl");
        returnUrl = StringUtils.isEmpty(returnUrl) ? "/" : returnUrl;

        logger.info(String.format("login returnUrl 1: %s", returnUrl));

        try {
            returnUrl = URLDecoder.decode(returnUrl, "utf-8");
        }catch (Exception e) {
            throw new NetPlusException("returnUrl解析异常");
        }

        logger.info(String.format("login returnUrl 2: %s", returnUrl));

        modelMap.put("returnUrl", returnUrl);

        // 如果已经登录，跳转
        if (!StringUtils.isEmpty(getUserId())) {
            return "redirect:/";
        }

        return "login";
    }

    @Anonymous
    @GetMapping("/login2")
    public String login2(HttpServletRequest request, ModelMap modelMap) {
        String returnUrl = request.getParameter("returnUrl");
        returnUrl = StringUtils.isEmpty(returnUrl) ? "/" : returnUrl;

        logger.info(String.format("login2 returnUrl 1: %s", returnUrl));

        try {
            returnUrl = URLDecoder.decode(returnUrl, "utf-8");
        }catch (Exception e) {
            throw new NetPlusException("returnUrl解析异常");
        }

        logger.info(String.format("login2 returnUrl 2: %s", returnUrl));

        modelMap.put("returnUrl", returnUrl);

        // 如果已经登录，跳转
        if (!StringUtils.isEmpty(getUserId())) {
            return "redirect:/";
        }
        return "login2";
    }

    @Anonymous
    @ResponseBody
    @PostMapping(value = "/doLogin/")
    public LoginResult doLogin(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        LoginResult loginResult = identityRest.doLogin(loginRequest);
        if (EnumLoginCode.OK.getValue().equals(loginResult.getCode())) {
            Cookie authCookie = new Cookie(SysConstant.AuthToken, loginResult.getAuthToken());
            authCookie.setHttpOnly(true);
            authCookie.setPath("/");
            response.addCookie(authCookie);
        }
        return loginResult;
    }

    @Anonymous
    @ResponseBody
    @PostMapping(value = "/checkPhone/")
    public ApiResponse checkPhone(@RequestBody CheckPhoneRequest checkPhoneRequest,
                                  HttpServletRequest request, HttpServletResponse response) {
        LoginResult res = new LoginResult();
        // 验证逻辑
        return ApiResponse.success(res);
    }

    @GetMapping(value = "/failed/")
    public String failed() {
        return "failed";
    }


    @GetMapping(value = "/logout")
    public String logout(HttpServletResponse response, HttpServletRequest request) {

        Cookie authCookie = new Cookie(SysConstant.AuthToken, null);
        Cookie Login = new Cookie(SysConstant.Login, null);
        authCookie.setPath("/");
        authCookie.setMaxAge(0);
        response.addCookie(authCookie);
        response.addCookie(Login);

        return String.format("redirect:https://%s", request.getServerName());
    }

    /**
     * @Description: 协议阅读并接受页面
     * @author: jinx
     * @date: 2020/12/28 13:27
     */
    @Anonymous
    @GetMapping("/register/")
    public String register() {
        return "register";
    }

    /**
     * @Description: 注册信息填写页面
     * @author: jinx
     * @date: 2020/12/28 13:27
     */
    @Anonymous
    @GetMapping("/register2")
    public String register2() {
        return "register2";
    }

    /**
     * @Description: 注册完成页面
     * @author: jinx
     * @date: 2020/12/28 13:27
     */
    @Anonymous
    @GetMapping("/register3")
    public ModelAndView register3(HttpServletRequest req) {
        //供应商账号
        String supplierCode = req.getParameter("supplierCode");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register3");
        modelAndView.addObject("supplierCode", supplierCode);

        return modelAndView;
    }



    @PreAuth(privilegeCode = "PG0034")
    @GetMapping("/profile/index")
    public String basicInformation() {
        return "basicInformation";
    }


    @Anonymous
    @GetMapping("/retrieve/")
    public String retrieve() {
        return "retrieve";
    }

    @PreAuth(privilegeCode = "PG0037")
    @GetMapping("/update-password/index")
    public String updatePassword() {
        return "updatePassword";
    }

    @Anonymous
    @GetMapping("/retrievePassword/")
    public ModelAndView retrievePassword() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("retrievePassword");
        modelAndView.addObject("msg", "");
        return modelAndView;
    }

    @Anonymous
    @GetMapping("/retrievePassword2/")
    public ModelAndView retrievePassword2(HttpServletRequest req) {
        String sessionId = req.getParameter("sessionId");
        ModelAndView modelAndView = new ModelAndView();

        //验证令牌是否有效
        String mobile=null;
        if(!StringUtils.isEmpty(sessionId)){
            mobile=cacheRest.get(sessionId);
        }

        if (StringUtils.isEmpty(mobile)) {
            modelAndView.addObject("msg", "短信验证码已失效，请重新获取");
            modelAndView.setViewName("retrievePassword");
            return modelAndView;
        }
        modelAndView.setViewName("retrievePassword2");
        modelAndView.addObject("sessionId", sessionId);
        return modelAndView;
    }

    @Anonymous
    @GetMapping("/retrievePassword3/")
    public String retrievePassword3() {
        return "retrievePassword3";
    }

    @Anonymous
    @GetMapping("/retrieveUsername/")
    public ModelAndView retrieveUsername() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("retrieveUsername");
        modelAndView.addObject("msg", "");
        return modelAndView;
    }

    @Anonymous
    @GetMapping("/retrieveUsername2/")
    public ModelAndView retrieveUsername2(HttpServletRequest req) {
        ModelAndView modelAndView = new ModelAndView();
        String sessionId = req.getParameter("sessionId");
        if (StringUtils.isEmpty(sessionId)) {
            modelAndView.addObject("msg", "短信验证码已失效，请重新获取");
            modelAndView.setViewName("retrieveUsername");
            return modelAndView;
        }
        modelAndView.setViewName("retrieveUsername2");
        modelAndView.addObject("sessionId", sessionId);
        return modelAndView;
    }
    @PreAuth(privilegeCode = "PG0030")
    @GetMapping("/workbench")
    public String workbench() {
        return "workbench";
    }

    @PreAuth(privilegeCode = "PG0003")
    @GetMapping("/staff")
    public String staff() {
        return "staff";
    }

    @PreAuth(privilegeCode = "PG0001")
    @GetMapping("/permissions")
    public String permissions() {
        return "permissions";
    }

    @PreAuth(privilegeCode = "PG0002")
    @GetMapping("/rolemanager")
    public String rolemanager() {
        return "rolemanager";
    }

    @PreAuth(privilegeCode = "PG0001")
    @GetMapping("/permissonSet")
    public String permissonSet(String roleCode, Model model) {
        model.addAttribute("roleCode", roleCode);
        return "permissonSet";
    }


    @PreAuth(privilegeCode = "PG0017")
    @GetMapping("/pur-workbench")
    public String purWorkbench() {
        return "purWorkbench";
    }


    @PreAuth(privilegeCode = "PG0039")
    @GetMapping("/serviceConfig")
    public String serviceConfig() {
        return "serviceConfig";
    }

    @PreAuth(privilegeCode = "PG0004")
    @GetMapping("/mock")
    public String mock() {
        return "mock";
    }


    @PreAuth(privilegeCode = "PG0025")
    @GetMapping("/apilog/businessLog")
    public String businessLog() {
        return "apilog/businessLog";
    }


    @PreAuth(privilegeCode = "PG0025")
    @GetMapping("/apilog/loginLog")
    public String loginLog() {
        return "apilog/loginLog";
    }


    @ResponseBody
    @PostMapping(value = "/mockLogin/")
    public LoginResult mockLogin(@RequestBody MockLoginRequest mockLoginRequest, HttpServletResponse response) {


        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLoginMethod("S");
        loginRequest.setUsername(loginUserBean.getUserCode());
        loginRequest.setPassword(mockLoginRequest.getPassword());

        loginRequest.setSource(mockLoginRequest.getSource());
        loginRequest.setMockUsername(mockLoginRequest.getMockUsername());

        LoginResult loginResult = identityRest.doLogin(loginRequest);
        if (EnumLoginCode.OK.getValue().equals(loginResult.getCode())) {
            Cookie authCookie = new Cookie(SysConstant.AuthToken, loginResult.getAuthToken());
            authCookie.setHttpOnly(true);
            authCookie.setPath("/");
            response.addCookie(authCookie);
        }else{
            throw new NetPlusException(loginResult.getMsg());
        }

        return loginResult;
    }
}
