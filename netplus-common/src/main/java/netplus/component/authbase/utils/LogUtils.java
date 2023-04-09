package netplus.component.authbase.utils;


import netplus.component.entity.api.ApiLog;
import netplus.component.entity.api.HttpLog;
import netplus.component.entity.api.IHttpLog;
import netplus.component.entity.consts.SysConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

public class LogUtils {

    private static Log logger = LogFactory.getLog(LogUtils.class);

    public static void before(HandlerMethod handlerMethod, IHttpLog iHttpLog, HttpServletRequest request){

        logger.debug(String.format("%s: %s", handlerMethod.getMethod().getName(), request.getRequestURI()));

        // 新增日志
        if (iHttpLog != null) {
            Method method = handlerMethod.getMethod();
            HttpLog httpLogAnnotation = method.getAnnotation(HttpLog.class);

            if (httpLogAnnotation != null) {
                String requestBody = request.getParameter("JSON") == null ? "" : request.getParameter("JSON");
                ApiLog apiLog = new ApiLog();
                apiLog.setApiName(request.getRequestURI());
                apiLog.setContent(requestBody.length() > 1000 ? requestBody.substring(0, 1000) : requestBody);
                apiLog.setCreateTime(new Date());
                apiLog.setDirection(1);
                apiLog.setResponse(null);
                apiLog.setUpdateTime(null);
                apiLog.setCreateUser(String.valueOf(request.getAttribute(SysConstant.UserID)));
                apiLog.setRequestIp(ToolUtils.getIpAddress(request));
                apiLog.setLogType(1);
                apiLog.setDescription(httpLogAnnotation.description());
                iHttpLog.insert(apiLog);

                request.setAttribute("logId", apiLog.getId());
            }
        }
    }


    public static void end(IHttpLog iHttpLog, HttpServletRequest request, HttpServletResponse response, Exception ex){

        if (iHttpLog != null) {
            Integer logId = (Integer) request.getAttribute("logId");
            if (logId != null){
                String responseText = String.format("status: %s, exception: %s",
                        response.getStatus(), ex == null ? "" : ex.getMessage());

                ApiLog apiLog = new ApiLog();
                apiLog.setId(logId);
                apiLog.setUpdateTime(new Date());
                apiLog.setResponse(responseText.length() > 1000 ? responseText.substring(0, 1000) : responseText);
                iHttpLog.updateByPrimaryKeySelective(apiLog);
            }
        }

    }

}
