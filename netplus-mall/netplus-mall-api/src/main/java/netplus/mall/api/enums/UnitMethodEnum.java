package netplus.mall.api.enums;

import netplus.component.entity.exceptions.NetPlusException;

/**
 * Created by py on 2021/7/15.
 */
public enum  UnitMethodEnum {

    AJ("AJ", "计件"),
    AL("AL", "散装");

    private String code;
    private String name;

    UnitMethodEnum (String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode () {
        return code;
    }

    public String getName () {
        return name;
    }

    public static String getCode (String name) {

        for (UnitMethodEnum e: UnitMethodEnum.values()) {
            if (e.getName().equals(name)) {
                return e.getCode();
            }
        }

        throw new NetPlusException("购买方式代码不存在");
    }


    public static String getName (String code) {

        for (UnitMethodEnum e: UnitMethodEnum.values()) {
            if (e.getCode().equals(code)) {
                return e.getName();
            }
        }

        throw new NetPlusException("购买方式名称不存在");
    }


}
