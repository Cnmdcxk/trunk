package netplus.component.authbase.utils;


import netplus.component.authbase.interceptor.AuthError;
import netplus.component.entity.auth.Anonymous;
import netplus.component.entity.consts.SysConstant;
import netplus.utils.secure.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthUtils {

    private static Log logger = LogFactory.getLog(AuthUtils.class);


    public static void check(Method method, HttpServletRequest request, HttpServletResponse response, String signKey) throws AuthError {

        // 检查token
        // 1、从请求路径取
        String authToken = request.getParameter(SysConstant.AuthToken);


        if (StringUtils.isEmpty(authToken)) {

            // 2、从header取
            authToken = request.getHeader(SysConstant.AuthToken);

            if (StringUtils.isEmpty(authToken)) {

                // 3、从cookie取
                authToken = RequestUtils.getCookieValue(SysConstant.AuthToken);

                if (!StringUtils.isEmpty(authToken))
                    RequestUtils.reflectSetHeader(SysConstant.AuthToken, authToken);
            }

        }else{

            RequestUtils.reflectSetHeader(SysConstant.AuthToken, authToken);

            Cookie authCookie = new Cookie(SysConstant.AuthToken, request.getParameter(SysConstant.AuthToken));
            authCookie.setHttpOnly(true);
            authCookie.setPath("/");
            response.addCookie(authCookie);
        }


        logger.info(String.format("authToken: [%s]", authToken));

        if (StringUtils.isEmpty(authToken)) {
            Anonymous methodAnnotation = method.getAnnotation(Anonymous.class);
            if (methodAnnotation == null)
                throw new AuthError(HttpStatus.FORBIDDEN.value(), "无Token，请重新登录");
        } else {

            if (!JwtUtil.verify(authToken, signKey)) {
                // 修复: 如果authToken不通过，如果有Anonymous标记，应该放行
                Anonymous methodAnnotation = method.getAnnotation(Anonymous.class);
                if (methodAnnotation == null)
                    throw new AuthError(HttpStatus.FORBIDDEN.value(), "Token已过期，请重新登录");
            } else {
                String companyCode = JwtUtil.getValue(authToken, "companyCode");
                request.setAttribute("companyCode", companyCode);

                String userNo = JwtUtil.getValue(authToken, "userNo");
                request.setAttribute(SysConstant.UserID, userNo);


                logger.info(String.format("companyCode: [%s]", companyCode));
                logger.info(String.format("userNo: [%s]", userNo));



                if (StringUtils.isNotEmpty(userNo)) {
                    request.setAttribute("role", userNo.startsWith("M") ? "S" : "B");
                }else{
                    request.setAttribute("role", " ");
                }


                // 如果即将过期，重新颁发token
                if (JwtUtil.canReSign(authToken)){
                    String newAuthToken = JwtUtil.signAgain(authToken, signKey);
                    Cookie cookie = new Cookie("AuthToken", newAuthToken);
                    cookie.setMaxAge(-1);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
    }
}
