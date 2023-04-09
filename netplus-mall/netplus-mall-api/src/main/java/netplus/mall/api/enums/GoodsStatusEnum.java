package netplus.mall.api.enums;


import netplus.component.entity.exceptions.NetPlusException;

public enum GoodsStatusEnum {

    status_10("10", "草稿"),
    status_15("15", "未上架"),

    status_20("20", "上架确认中"), //作废

    status_25("25", "已上架"),

    status_30("30", "上架驳回"), //作废

    status_35("35", "下架确认中"),
    status_40("40", "下架驳回"),
    status_45("45", "失效");

    private String code;
    private String name;

    GoodsStatusEnum (String code, String name) {
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

        for (GoodsStatusEnum e: GoodsStatusEnum.values()) {
            if (e.getCode().equals(code)) {
                return e.getName();
            }
        }

        throw new NetPlusException("状态代码不存在");
    }

    public static boolean isShelvesCode(String code){
        if(status_25.getCode().equals(code)
                || status_35.getCode().equals(code)
                || status_40.getCode().equals(code)){
            return true;
        }
        return false;
    }


}
