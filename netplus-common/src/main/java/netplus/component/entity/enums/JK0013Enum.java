package netplus.component.entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum JK0013Enum {
    /**
     * 永钢易购注册验证码
     */
    REGISTER("3975195e-0bbf-11ec-8abc-005056ab2101",new HashMap<String,String>(){{
        put("1","");
    }}),

    /**
     * 永钢易购接单作废
     */
    ORDER_CANCEL("40bcf885-3e93-11ec-8abc-005056ab2101",new HashMap<String,String>(){{
        put("1","");
        put("2","");
        put("3","");
    }}),

    /**
     * 永钢易购开户短信
     */
    OPEN_ACCOUNT("543ab1fe-3e91-11ec-8abc-005056ab2101",new HashMap<String,String>(){{
        put("1","");
        put("2","");
    }}),

    /**
     * 永钢易购供应商开户失败通知
     */
    OPEN_ACCOUNT_FAILED("83ce02a0-3e92-11ec-8abc-005056ab2101",new HashMap<String,String>(){{
        put("1","");
        put("2","");
    }}),

    /**
     * 永钢易购项目供应商开户数量通知
     */
    SUPPLIER_OPEN_ACCOUNT_NUM("bec439fd-3e91-11ec-8abc-005056ab2101",new HashMap<String,String>(){{
        put("1","");
    }}),

    /**
     * 永钢易购接单通知供应商
     */
    ACCEPT_ORDER_INFORM_EMP("c299d10f-3e92-11ec-8abc-005056ab2101",new HashMap<String,String>(){{
        put("1","");
        put("2","");
        put("3","");
    }}),

    /**
     * 永钢易购接单确认通知永钢员工
     */
    ACCEPT_ORDER_CONFIRM("fe3a7a7b-3e92-11ec-8abc-005056ab2101",new HashMap<String,String>(){{
        put("1","");
        put("2","");
        put("3","");
    }}),

    /**
     * 永钢易购发货通知
     */
    DELIVER_GOODS_INFORM("91e692e6-3e93-11ec-8abc-005056ab2101",new HashMap<String,String>(){{
        put("1","");
    }}),

    /**
     * 供应商未接单物资采购人短信提醒
     */
    AGENT_ACCEPT_ORDER_WARN("832fb5f7-afdc-11ec-8abc-005056ab2101",new HashMap<String,String>(){{
        put("1","");
        put("2","");
        put("3","");
    }}),

    ;

    private String templateId;
    private Map<String,String> params;

    public String getTemplateId() {
        return templateId;
    }

    public Map<String, String> getParams() {
        return params;
    }

    JK0013Enum(String templateId, Map<String, String> params) {
        this.templateId = templateId;
        this.params = params;
    }
}
