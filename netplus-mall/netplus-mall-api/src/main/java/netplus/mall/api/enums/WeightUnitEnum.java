package netplus.mall.api.enums;


public enum WeightUnitEnum {

    QK("QK", "千克"),
    D("D", "吨"),
    K("K", "克"),
    J("J", "斤"),
    GJ("GJ", "公斤"),
    L("L", "两"),
    HK("HK", "毫克");

    private String code;
    private String name;

    WeightUnitEnum (String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode () {
        return code;
    }

    public String getName () {
        return name;
    }

    public static UnitMethodEnum getUnitMethod (String name) {


        for (WeightUnitEnum e: WeightUnitEnum.values()) {
            if (e.getName().equals(name)) {
                return UnitMethodEnum.AL;
            }
        }

        return UnitMethodEnum.AJ;
    }



}
