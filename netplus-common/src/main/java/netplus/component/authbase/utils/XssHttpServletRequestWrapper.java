package netplus.component.authbase.utils;

import netplus.utils.object.ObjectUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.MimeHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.lang.reflect.Field;

/**
 * 重新包装HttpServletRequest，重写请求参数处理函数
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    protected Log logger = LogFactory.getLog(XssHttpServletRequestWrapper.class);

    private HttpServletRequest orgRequest = null;

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }

    /**
     * 覆盖getParameter方法，将参数值做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */

    @Override
    public String getParameter(String name) {
        String value = XssUtil.clean(super.getParameter(XssUtil.clean(name)));
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(XssUtil.clean(name));
        if(arr != null){
            for (int i=0;i<arr.length;i++) {
                arr[i] = XssUtil.clean(arr[i]);
            }
        }
        return arr;
    }


    /**
     * 覆盖getHeader方法，将参数值做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
     * getHeaderNames 也可能需要覆盖
     */

    @Override
    public String getHeader(String name) {
        String value = XssUtil.clean(super.getHeader(XssUtil.clean(name)));
        return value;
    }

    /**
     * 获取最原始的request
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request的静态方法
     *
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
        }

        return req;
    }

    public Boolean setHeader(String key, String value){
        if (ObjectUtil.isEmpty(orgRequest)){
            return false;
        }

        Class<? extends HttpServletRequest> requestClass = orgRequest.getClass();
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(orgRequest);
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
        return true;
    }

}
