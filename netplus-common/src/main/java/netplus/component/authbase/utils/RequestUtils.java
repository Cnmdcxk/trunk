package netplus.component.authbase.utils;


import org.apache.tomcat.util.http.MimeHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

public class RequestUtils {


    private static Logger logger = LoggerFactory.getLogger(RequestUtils.class);


    public static String getClientIP(){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ipAddr = null;
        if (realIp == null) {
            if (forwarded == null) {
                ipAddr = remoteAddr;
            } else {
                ipAddr = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ipAddr = realIp;
            } else {
                if (forwarded != null) {
                    forwarded = forwarded.split(",")[0];
                }
                ipAddr = realIp + "/" + forwarded;
            }
        }
        return ipAddr;
    }


    public static String getUserAgent(){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        return request.getHeader("user-agent") == null ? "" : request.getHeader("user-agent");

    }


    public static String getCookieValue(String cookieName){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        if(request.getCookies() != null){
            for(Cookie cookie: request.getCookies()){
                if (cookie.getName().equals(cookieName))
                    return cookie.getValue();
            }
        }
        return null;
    }


    // 使用反射的方式设置header
    public static void reflectSetHeader(String key, String value) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        if (attributes.getRequest() instanceof XssHttpServletRequestWrapper){
            XssHttpServletRequestWrapper request = (XssHttpServletRequestWrapper)attributes.getRequest();
            request.setHeader(key, value);
        }
        else{
            HttpServletRequest request = attributes.getRequest();

            Class<? extends HttpServletRequest> requestClass = request.getClass();
            try {
                Field request1 = requestClass.getDeclaredField("request");
                request1.setAccessible(true);
                Object o = request1.get(request);
                Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
                coyoteRequest.setAccessible(true);
                Object o1 = coyoteRequest.get(o);
                Field headers = o1.getClass().getDeclaredField("headers");
                headers.setAccessible(true);
                MimeHeaders o2 = (MimeHeaders) headers.get(o1);
                o2.addValue(key).setString(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
