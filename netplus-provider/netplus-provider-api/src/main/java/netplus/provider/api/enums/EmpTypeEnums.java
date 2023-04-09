package netplus.provider.api.enums;

public enum  EmpTypeEnums {


    one("1", "下单用户"),
    two("2", "收货用户");

    private String code;
    private String name;

    EmpTypeEnums (String code, String name) {
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

        for (EmpTypeEnums e: EmpTypeEnums.values()) {
            if (e.getCode().equals(code)) {
                return e.getName();
            }
        }

        return "";
    }


    public static String getCode (String name) {

        for (EmpTypeEnums e: EmpTypeEnums.values()) {
            if (e.getName().equals(name)) {
                return e.getCode();
            }
        }

        return "";
    }

}
