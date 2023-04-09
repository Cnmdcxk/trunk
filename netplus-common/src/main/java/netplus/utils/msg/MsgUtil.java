package netplus.utils.msg;


public class MsgUtil {




    //消息格式
    public enum MsgId {

        Order001("Order001", "您有来自【%s】的采购合同【%s】已生成", MsgType.Order),


        Approve001("Id002", "您的审批单号【%s】被【%s-%s】审批驳回", MsgType.Approve),
        Approve002("Id003", "您有来自【%s-%s】的【%s】审批单待审批", MsgType.Approve),


        GoodAudit001("GoodAudit001", "您有来自于【%s】的【%d】条商品待挂牌审核", MsgType.GoodAudit);

        private String id;
        private String content;
        private MsgType msgType;

        MsgId (String id, String content, MsgType msgType) {
            this.id = id;
            this.content = content;
            this.msgType = msgType;
        }

        public String getId () {
            return id;
        }

        public String getContent () {
            return content;
        }

        public String getMsgTypeCode () {
            return msgType.getCode();
        }

        public String getMsgContent (String... s) {
            return String.format(content, s);
        }

        //消息类型
        public enum MsgType {

            Order("MQQCG003", "订单提醒"),
            Approve("MQQCG002", "审批提醒"),
            GoodAudit("MQQCG001", "商品审核提醒");

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
}


