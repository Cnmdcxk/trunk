package netplus.provider.api.enums;

public enum ApiLogTypeEnums {
    METHOD("0","方法访问日志"),
    ERROR("1","异常日志"),
    BUSINESS("2","业务日志");

    private String code;
    private String name;

    ApiLogTypeEnums(String code, String name) {
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

        for (ApiLogTypeEnums e: ApiLogTypeEnums.values()) {
            if (e.getCode().equals(code)) {
                return e.getName();
            }
        }

        return "";
    }


    public static String getCode (String name) {

        for (ApiLogTypeEnums e: ApiLogTypeEnums.values()) {
            if (e.getName().equals(name)) {
                return e.getCode();
            }
        }

        return "";
    }
}
