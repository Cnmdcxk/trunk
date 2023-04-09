package netplus.component.authbase.interceptor;

import netplus.component.authbase.utils.*;
import netplus.component.entity.api.IHttpLog;
import netplus.component.entity.auth.IAccess;
import netplus.component.entity.auth.IPrivilege;
import netplus.component.entity.version.IVersion;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static Log logger = LogFactory.getLog(AuthInterceptor.class);

    public AuthInterceptor(){
    }

    @Autowired(required = false)
    private IAccess iAccess;
    private IAccess localAccessImpl;
    public AuthInterceptor(IAccess localAccessImpl){
        this.localAccessImpl = localAccessImpl;
    }

    @Autowired(required = false)
    private IVersion iVersion;
    private IVersion localVersionImpl;
    public AuthInterceptor(IVersion localVersionImpl){ this.localVersionImpl = localVersionImpl; }


    @Autowired(required = false)
    private IPrivilege iPrivilege;

    // begin
    @Value("${cdn.url}")
    private String cdn;
    @Value("${environment.desc}")
    private String environmentDesc;
    @Value("${spring.loginUrl}")
    private String loginUrl;
    @Value("${sign.key}")
    private String signKey;
    // end

    @Autowired(required = false)
    private IHttpLog iHttpLog;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        request.setAttribute("cdn", this.cdn);
        request.setAttribute("environmentDesc", this.environmentDesc);

        // 非状态为200的不处理，如404等
        if (response.getStatus() != HttpStatus.OK.value()) {
            return true;
        }

        //
        if (handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod)handler;
            Method method = handlerMethod.getMethod();

            // 记录请求日志
            LogUtils.before(handlerMethod, iHttpLog, request);

            // 检查access token
            // 检查auth token
            // 检查访问权限
            try{
                //

                IAccess access = (localAccessImpl == null) ? iAccess : localAccessImpl;
                AccessUtils.check(method, request, access);

                // 设置静态文件版本,
                // 修复：401错误, accessToken还没赋值
                IVersion version = (localVersionImpl == null) ? iVersion : localVersionImpl;
                VersionUtils.setVer(version, request);

                //
                AuthUtils.check(method, request, response, signKey);


                //
                PrivilegeUtils.check(method, request, iPrivilege);

            }
            catch (AuthError ax){

                logger.error(ax.getMessage(), ax);

                return errResp(request, response, ax.getStatus(), ax.getMessage());
            }
        }

        return true;
    }


    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                @Nullable Exception ex) throws Exception {

        LogUtils.end(iHttpLog, request, response, ex);

    }


    private boolean errResp(HttpServletRequest request,
                            HttpServletResponse response, int status, String error) throws IOException {

        // 如果是get 直接跳转到登录页面
        if (request.getMethod().toLowerCase().equals("get")) {
            // 401: 没有权限
            // 403: 需要登录
            String targetUrl = (status == HttpStatus.FORBIDDEN.value()) ? loginUrl : "/provider/failed/";
            String returnUrl;

            if (StringUtils.isEmpty(request.getQueryString()))
                returnUrl = URLEncoder.encode(request.getRequestURI(), "utf-8");
            else
                returnUrl = URLEncoder.encode(String.format("%s?%s", request.getRequestURI(), request.getQueryString()), "utf-8");

            String fullUrl = String.format("https://%s%s?code=%s&returnUrl=%s",request.getServerName(),targetUrl, status, returnUrl);

            response.sendRedirect(fullUrl);
            return false;
        }

        response.addHeader("Content-Type", "text/html; charset=UTF-8");
        response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");

        response.setStatus(status);
        response.getWriter().append(error);
        return false;
    }
}
