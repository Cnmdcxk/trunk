package netplus.mall.api.enums;


public enum SourceFrom  {

    ERP("ERP", "ERP系统"),
    ZKH("ZKH", "震坤行系统");

    private String code;
    private String name;

    SourceFrom (String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode () {
        return code;
    }

    public String getName () {
        return name;
    }
}
