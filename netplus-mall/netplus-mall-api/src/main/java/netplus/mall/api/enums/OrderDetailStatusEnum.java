package netplus.mall.api.enums;

import netplus.component.entity.exceptions.NetPlusException;

public enum  OrderDetailStatusEnum {

    status_10("10", "审核中"),
    status_15("15", "待接单"),
    status_20("20", "已驳回"),
    status_25("25", "交货中"),
    status_30("30", "已到货"),
    status_35("35", "已完结"),
    status_40("40", "已撤销"),
    status_45("45", "已作废");

    private String code;
    private String name;

    OrderDetailStatusEnum (String code, String name) {
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

        for (OrderDetailStatusEnum e: OrderDetailStatusEnum.values()) {
            if (e.getCode().equals(code)) {
                return e.getName();
            }
        }

        throw new NetPlusException("状态代码不存在");
    }
}
