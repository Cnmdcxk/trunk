package netplus.mall.api.enums;

import netplus.component.entity.exceptions.NetPlusException;

/**
 * Created by py on 2021/9/6.
 */
public enum PayTypeEnum {

    A("A", "预付款"),
    B("B", "进度款"),
    C("C", "提货款"),
    D("D", "交货完成"),
    E("E", "馀款"),
    F("F", "帐期付款"),
    G("G", "质保金"),
    L("L", "信用状承兑"),
    M("M", "信用证保证金"),
    N("N", "调试验收");

    private String code;
    private String name;

    PayTypeEnum (String code, String name) {
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

        for (PayTypeEnum e: PayTypeEnum.values()) {
            if (e.getCode().equals(code)) {
                return e.getName();
            }
        }

        throw new NetPlusException("付款类型代码不存在");
    }
}
