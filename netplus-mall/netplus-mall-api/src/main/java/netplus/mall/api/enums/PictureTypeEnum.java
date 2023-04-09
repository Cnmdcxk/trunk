package netplus.mall.api.enums;

/**
 * Created by py on 2021/7/15.
 */
public enum  PictureTypeEnum {

    MAIN("1", "主图"),
    ASSISTANT("2", "详情图");

    private String code;
    private String name;

    PictureTypeEnum (String code, String name) {
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
