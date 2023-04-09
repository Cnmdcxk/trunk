package netplus.mall.api.enums;

import netplus.component.entity.exceptions.NetPlusException;

/**
 * Created by py on 2021/9/6.
 */
public enum PayMethodEnum {

    PM_10("10", "现金支票"),
    PM_11("11", "转账支票"),
    PM_20("20", "电汇款"),
    PM_21("21", "现金"),
    PM_23("23", "业务委托书"),
    PM_24("24", "应付票据-应收款链电子汇票"),
    PM_30("30", "应收票据-应收款链电子汇票"),
    PM_31("31", "应收银行承兑汇票"),
    PM_32("32", "应收商业承兑汇票"),
    PM_33("33", "应付商业承兑汇票"),
    PM_53("53", "内部单位存款-基本户-承兑"),
    PM_92("92", "中转科目-往来调整"),
    PM_93("93", "营业外收入(放弃债权)"),
    PM_99("99", "付款单调账"),
    PM_A0("A0", "信托"),
    PM_A1("A1", "信用证");

    private String code;
    private String name;

    PayMethodEnum (String code, String name) {
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

        for (PayMethodEnum e: PayMethodEnum.values()) {
            if (e.getCode().equals(code)) {
                return e.getName();
            }
        }

        throw new NetPlusException("付款方式代码不存在");
    }

}
