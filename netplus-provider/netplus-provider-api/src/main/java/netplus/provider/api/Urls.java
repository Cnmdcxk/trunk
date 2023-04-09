package netplus.provider.api;

public class Urls {

    public static final String getCurrentUser = "/api/provider/getCurrentUser/";
    public static final String getUserPrivileges = "/api/provider/getUserPrivileges";
    public static final String doLogin = "/api/provider/doLogin/";

    public static final String retrievePasswordSendSmsCode = "/api/provider/retrievePassword/sendSmsCode/";
    //找回账号或密码下一步执行动作
    public static final String retrieve = "/api/provider/retrieve/";
    //找回账号或密码下一步执行动作
    public static final String updPassword = "/api/provider/updPassword/";

    // 用户修改个人密码
    public static final String updateUserPwd = "/api/provider/updateUserPwd";

    public static final String getUserByUserNo = "/api/provider/getUserByUserNo";

    public static final String updateSupplierBizContact = "/api/provider/updateSupplierBizContact";
    public static final String getSupplierBizContact = "/api/provider/getSupplierBizContact";
    public static final String getProviderTokenInfo = "/api/provider/getProviderTokenInfo";

    public static final String syncProvider = "/api/provider/syncProvider";
    public static final String syncDepartment = "/api/provider/syncDepartment";
    public static final String getYgUser = "/api/provider/getYgUser";
    public static final String getSupplierNo = "/api/provider/getSupplierNo";
    public static final String getSupplierByName = "/api/provider/getSupplierByName";
    public static final String getSupplierNoList = "/api/provider/getSupplierNoList";
    public static final String getDepartment = "/api/provider/getDepartment";
    public static final String chatLogin = "/api/provider/chatLogin";


    public class Workbench {
        public static final String getSupplier = "/api/provider/getSupplier/";
    }

    //职员
    public class Staff {

        public static final String getStaffList = "/api/provider/getStaffList/";
        public static final String updateRole = "/api/provider/updateRole/";
        public static final String updateActive = "/api/provider/updateActive/";
        public static final String exportStaff = "/api/provider/exportStaff/";
        public static final String getUserOwnRoleByUserNo = "/api/provider/getUserOwnRoleByUserNo/";
        public static final String getConsignee = "/api/provider/getConsignee/";
        public static final String getConsigneeByUserNo = "/api/provider/getConsigneeByUserNo/";
        public static final String getUserRoleList = "/api/provider/getUserRoleList/";




    }


    public static class permissions {
        public static final String getAllList = "/api/provider/getAllList/";
        public static final String addPrivilege = "/api/provider/addPrivilege/";
        public static final String modifyPrivilege = "/api/provider/modifyPrivilege/";
        public static final String getByCodeList = "/api/provider/getByCodeList/";
        public static final String getUserMenuList = "/api/provider/getUserMenuList/";

    }

    public static class RolemanagerData {
        public static final String getList = "/api/provider/getList";
        public static final String delete = "/api/provider/delete";
        public static final String islist = "/api/provider/islist";
        public static final String addrole = "/api/provider/addrole";
        public static final String getListPermissions = "/api/provider/getListPermissions";
        public static final String getRolePrivilegeList = "/api/provider/getRolePrivilegeList";
        public static final String saveRolePrivilege = "/api/provider/saveRolePrivilege";
        public static final String getRoleOwnPrivileges = "/api/provider/getRoleOwnPrivileges";
        public static final String updaterole = "/api/provider/updaterole";
    }

    public static class Common {

        public static final String getAddrList = "/api/provider/getAddrList/";
        public static final String getUserPageList = "/api/provider/getUserPageList/";
        public static final String getRoleListByUser = "/api/provider/getRoleListByUser/";
    }

    public static class ServiceData {
        public static final String getListService = "/api/provider/getListService";
        public static final String addServiceConfig = "/api/provider/addServiceConfig";
        public static final String updateServiceConfig = "/api/provider/updateServiceConfig";
        public static final String deleteServiceConfig = "/api/provider/deleteServiceConfig";
        public static final String getServiceConfigByCode = "/api/provider/getServiceConfigByCode";

    }

    public static class ApiLog{
        public static final String getBusinessLogPage="/api/provider/getBusinessLogPage";
        public static final String getLoginLogPage="/api/provider/getLoginLogPage";
        public static final String exportBusinessLog="/api/provider/exportBusinessLog";
        public static final String exportLoginLog="/api/provider/exportLoginLog";
    }
}
