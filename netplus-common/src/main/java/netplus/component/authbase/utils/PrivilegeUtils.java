package netplus.component.authbase.utils;


import netplus.component.authbase.interceptor.AuthError;
import netplus.component.entity.auth.IPrivilege;
import netplus.component.entity.auth.PreAuth;
import netplus.component.entity.consts.SysConstant;
import netplus.component.entity.exceptions.NetPlusException;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class PrivilegeUtils {

    public static void check(Method method, HttpServletRequest request, IPrivilege iPrivilege) throws AuthError {
        PreAuth preAuth = method.getAnnotation(PreAuth.class);
        if (preAuth != null) {
            Object userId = request.getAttribute(SysConstant.UserID);
            if (userId == null) {
                throw new AuthError(HttpStatus.FORBIDDEN.value(), "未登录用户无访问权限");
            } else if (iPrivilege == null) {
                throw new NetPlusException("iPrivilege未初始化");
            } else if (!iPrivilege.getUserPrivileges().contains(preAuth.privilegeCode())) {
                throw new AuthError(HttpStatus.UNAUTHORIZED.value(), String.format("错误代码：%s", preAuth.privilegeCode()));
            } else {
                request.setAttribute(SysConstant.PrivilegeCode, preAuth.privilegeCode());
            }
        }
    }
}
