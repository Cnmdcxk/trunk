package netplus.component.feignclient;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
public class FeignInterceptor implements RequestInterceptor {

    private static Log logger = LogFactory.getLog(FeignInterceptor.class);

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();


        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {

                String accessToken = request.getHeader("AccessToken");
                String authToken = request.getHeader("AuthToken");

                if (null != accessToken)
                    template.header("AccessToken", accessToken);
                if (null != authToken)
                    template.header("AuthToken", authToken);

                // 转发IP
                String xForwardedFor = request.getHeader("X-Forwarded-For");
                String xRealIP = request.getHeader("X-Real-IP");

                if (null != xForwardedFor)
                    template.header("X-Forwarded-For", xForwardedFor);
                if (null != xRealIP)
                    template.header("X-Real-IP", xRealIP);

                // 如果都为空记录日志
                if (StringUtils.isEmpty(accessToken) && StringUtils.isEmpty(authToken))
                    logger.warn(String.format("%s - accessToken: [%s], authToken: [%s]",
                            request.getRequestURI(), accessToken, authToken));


                String userAgent = request.getHeader("User-Agent");
                if(null != userAgent){
                    template.header("User-Agent", userAgent);
                }


            }
        }
    }
}
