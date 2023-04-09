package netplus.component.entity.enums;

public enum  MsgId {

    Order001("Order001", "您的采购电商平台的【%s】的销售合同【%s】已生成,本讯息仅为通知，审批流程请在电商平台进行", MsgType.Order),
    Order002("Order002", "您的采购电商平台的【%s】审批单被【%s-%s】审批驳回，本讯息仅为通知，审批流程请在电商平台进行", MsgType.Order),


    Approve001("Approve001", "您的采购电商平台的【%s】被【%s-%s】审批驳回，本讯息仅为通知，审批流程请在电商平台进行", MsgType.Approve),
    Approve002("Approve002", "您的采购电商平台有来自【%s-%s】的【%s】审批单待审批,本讯息仅为通知，审批流程请在电商平台进行", MsgType.Approve),

    //撤回审批动作
    Approve003("Approve003", "", MsgType.Approve),


    GoodAudit001("GoodAudit001", "您的采购电商平台的【%s】的【%s】条商品待修改审核,本讯息仅为通知，审批流程请在电商平台进行", MsgType.GoodAudit),
    GoodAudit002("GoodAudit002", "您的采购电商平台的【%s】条商品修改审核通过,本讯息仅为通知，审批流程请在电商平台进行", MsgType.GoodAudit),
    GoodAudit003("GoodAudit003", "您的采购电商平台的【%s】条商品修改审核被【%s-%s】驳回,本讯息仅为通知，审批流程请在电商平台进行", MsgType.GoodAudit),

    GoodAudit004("GoodAudit004", "您的采购电商平台有来自于【%s】的【%s】条商品待挂牌审核,本讯息仅为通知，审批流程请在电商平台进行", MsgType.GoodAudit),
    GoodAudit005("GoodAudit005", "您的采购电商平台有【%s】条商品挂牌审核通过，已上架,本讯息仅为通知，审批流程请在电商平台进行", MsgType.GoodAudit),
    GoodAudit006("GoodAudit006", "您的采购电商平台有【%s】条商品挂牌审核被【%s-%s】驳回,本讯息仅为通知，审批流程请在电商平台进行", MsgType.GoodAudit),

    StockReturn001("StockReturn001", "您有来自采购电商平台的账套【%s-%s】请购项次号【%s】的请购项次被存量审核师【%s-%s】退回，该请购项次已作废，本讯息仅为通知，审批流程请在日钢采购电商平台进行", MsgType.Approve),
    StockReturn002("StockReturn002", "您有来自采购电商平台的请购项次号【%s】被采购人员【%s-%s】退回，本讯息仅为通知", MsgType.Approve),

    EntrustUrgent001("EntrustUrgent001", "加急委托计划:【%s】 ，请及时分配！！", MsgType.Entrust);




    private String id;
    private String format;
    private MsgType msgType;

    MsgId (String id, String format, MsgType msgType) {
        this.id = id;
        this.format = format;
        this.msgType = msgType;
    }

    public String getId () {
        return id;
    }

    public String getFormat () {
        return format;
    }

    public String getMsgTypeCode () {
        return msgType.getCode();
    }

    public String getContent (String... s) {
        return String.format(format, s);
    }

    //消息类型
    public enum MsgType {

        Order("MQQCG003", "订单提醒"),
        Approve("MQQCG002", "审批提醒"),
        GoodAudit("MQQCG001", "商品审核提醒"),
        Entrust("MQQCG004", "委托提醒");

        private String code;
        private String name;

        MsgType (String code, String name) {
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
}
