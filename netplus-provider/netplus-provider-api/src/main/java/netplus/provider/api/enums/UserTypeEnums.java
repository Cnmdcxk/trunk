package netplus.provider.api.enums;

import netplus.component.entity.exceptions.NetPlusException;

public enum UserTypeEnums {

    S("S", "供应商"),
    B("B", "员工");
    private String code;
    private String name;

    UserTypeEnums (String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode () {
        return code;
    }

    public String getName () {
        return name;
    }


    public static String getName (String code) {

        for (UserTypeEnums e: UserTypeEnums.values()) {
            if (e.getCode().equals(code)) {
                return e.getName();
            }
        }

        throw new NetPlusException("用户类型名称不存在");
    }


    public static String getCode (String name) {

        for (UserTypeEnums e: UserTypeEnums.values()) {
            if (e.getName().equals(name)) {
                return e.getCode();
            }
        }

        throw new NetPlusException("用户类型代码不存在");
    }

}
