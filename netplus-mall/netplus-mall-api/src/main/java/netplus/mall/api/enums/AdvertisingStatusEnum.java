package netplus.mall.api.enums;

public enum AdvertisingStatusEnum {

    UNPUBLISHED(0,"未发布"),
    PUBLISHED(1,"已发布");


    private int code;
    private String name;

    AdvertisingStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
