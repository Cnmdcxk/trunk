package netplus.component.entity.enums;

public enum ConfigureEnum {

    IS_SEND_DUANXIN("IS_SEND_DUANXIN","是否发送短信"),

    MALL_OUT_SUPPLIER("MALL_OUT_SUPPLIER", "外部供应商配置"),
    MALL_HOST("MALL_HOST", "商城主机名称"),
    MALL_HOST_MOBILE("MALL_HOST_MOBILE", "商城移动端主机名称"),
    MALL_TOP("MALL_TOP", "商城首页大类销量榜配置"),
    MALL_OFF_LINE_HANDLER("MALL_OFF_LINE_HANDLER", "下架申请处理人"),
    MALL_ON_LINE_HANDLER("MALL_ON_LINE_HANDLER", "上架申请处理人"),
    MALL_SPEC_USER("MALL_SPEC_USER", "商城特殊账号"),
    MALL_ORDER_ZKH_ZK_CONSIGNEE("MALL_ORDER_ZKH_ZK_CONSIGNEE", "商城订单震坤行总库收货人信息"),

    ROLE_DEMP("ROLE_DEMP", "默认员工角色代码"),
    ROLE_PROVIDER("ROLE_PROVIDER", "默认供应商角色"),
    DELIVERY_APPOINTMENT_URL("DELIVERY_APPOINTMENT_URL", "销售订单交货预约URL"),
    SPECIAL_CHECK_OUT_CATEGORY("SPECIAL_CHECK_OUT_CATEGORY", "特殊部门需单独下单的分类"),
    SPECIAL_CHECK_OUT_DEPT_NO("SPECIAL_CHECK_OUT_DEPT_NO", "结算特殊分类需单独下单的部门"),
    ORDER_NOT_RECEIVE_WARN_TIMES("ORDER_NOT_RECEIVE_WARN_TIMES", "订单未接单提醒时间"),
    SPECIAL_MATRL_QUALITY_PK("SPECIAL_MATRL_QUALITY_PK", "只能物资总库下单的物资定性代码"),
    SPECIAL_MATRL_QUALITY_DEPT_NO("SPECIAL_MATRL_QUALITY_DEPT_NO", "物资总库部门"),
    ;



    public String code;
    public String name;

    ConfigureEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
