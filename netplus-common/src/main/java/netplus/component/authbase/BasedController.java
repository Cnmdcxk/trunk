package netplus.component.authbase;

import com.google.gson.Gson;
import netplus.component.authbase.utils.RequestUtils;
import netplus.component.entity.consts.SysConstant;
import netplus.utils.json.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


public abstract class BasedController {


    protected Log logger = LogFactory.getLog(BasedController.class);


    // 子类用到
    public static final Gson gson = JsonUtil.JsonInstance;

    public String getAccessToken(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return (String) request.getAttribute(SysConstant.AccessToken);
    }

    public String getAuthToken(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return (String) request.getAttribute(SysConstant.AuthToken);
    }

    public String getAppID(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return (String) request.getAttribute(SysConstant.AppID);
    }

    public String getCompNo(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return (String) request.getAttribute("companyCode");
    }

    public String getUserId(){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String supplierno = (String) request.getAttribute(SysConstant.UserID);
        return supplierno;
    }

    public String getClientIP(){
        return RequestUtils.getClientIP();
    }

    public String getUserAgent(){
        return RequestUtils.getUserAgent();
    }

    public String getPrivilegeCode(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return (String) request.getAttribute(SysConstant.PrivilegeCode);
    }

    public boolean isMobile() {
        String[] mobileAgents = {"iphone", "android", "ipad", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
                "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
                "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
                "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
                "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
                "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
                "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
                "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
                "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
                "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
                "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
                "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
                "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
                "Googlebot-Mobile"};

        String agent = getUserAgent().toLowerCase();
        for (String mobileAgent : mobileAgents) {
            if (agent.contains(mobileAgent) &&
                    agent.indexOf("windows nt") <= 0 &&
                    agent.indexOf("macintosh") <= 0) {
                return true;
            }
        }

        return false;
    }
}
