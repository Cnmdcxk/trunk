package netplus.component.authbase.utils;


import netplus.component.authbase.interceptor.AuthError;
import netplus.component.entity.auth.AccessAnonymous;
import netplus.component.entity.auth.ApplicationVo;
import netplus.component.entity.auth.IAccess;
import netplus.component.entity.consts.SysConstant;
import netplus.component.entity.enums.YesNoEnum;
import netplus.component.entity.exceptions.NetPlusException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccessUtils {

    private static Log logger = LogFactory.getLog(AccessUtils.class);

    private static Map<String, String> accessTkMap = new ConcurrentHashMap<>();
    private static Map<String, String> domainAccessMap = new ConcurrentHashMap<>();

    public static void check(Method method, HttpServletRequest request, IAccess access) throws AuthError {

        if (access == null)
            throw new NetPlusException("iAccess未初始化");

        // 检查accessToken
        // 1、先从header取
        String accessToken = request.getHeader(SysConstant.AccessToken);
        if (StringUtils.isEmpty(accessToken)) {
            // 2、 然后从cookie取
            accessToken = RequestUtils.getCookieValue(SysConstant.AccessToken);

            if (StringUtils.isEmpty(accessToken)) {
                // 3、通过域名查询accessToken
                accessToken = getAccessFromDomain(access, request.getServerName().toLowerCase());
            }

            // 4、set to header，其他微服务需要
            if (!StringUtils.isEmpty(accessToken))
                RequestUtils.reflectSetHeader(SysConstant.AccessToken, accessToken);
        }

        if (StringUtils.isEmpty(accessToken)) {
            AccessAnonymous accessAnonymous = method.getAnnotation(AccessAnonymous.class);
            if (accessAnonymous == null)
                throw new AuthError(HttpStatus.UNAUTHORIZED.value(), "accessToken为空");
        } else {
            if (!accessTkMap.containsKey(accessToken)) {

                ApplicationVo application = access.selectByAccessToken(accessToken);
                if (application == null) {
                    throw new AuthError(HttpStatus.UNAUTHORIZED.value(), "accessToken无效");
                } else if (!application.getIsActive().equals(YesNoEnum.Yes.getValue())) {
                    throw new AuthError(HttpStatus.UNAUTHORIZED.value(), "accessToken已过期");
                } else {
                    accessTkMap.put(accessToken, application.getAppId());
                }
            }
            request.setAttribute(SysConstant.AccessToken, accessToken);
            request.setAttribute(SysConstant.AppID, accessTkMap.get(accessToken));
        }

    }

    private static String getAccessFromDomain(IAccess access, String domain) {
        if (domainAccessMap.containsKey(domain))
            return domainAccessMap.get(domain);
        else {
            String accessToken = access.selectAccessTokenByDomain(domain);
            if (!StringUtils.isEmpty(accessToken)) {
                domainAccessMap.put(domain, accessToken);
                return accessToken;
            }
        }
        return null;
    }


}
