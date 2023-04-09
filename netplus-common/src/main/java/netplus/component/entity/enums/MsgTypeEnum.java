package netplus.component.entity.enums;

/**
 * 消息类型
 */
public enum MsgTypeEnum {
    UP_SHELVES(0,"上架申请","您有来自于%s的%s条商品待上架确认",false ,MsgGroupEnum.LISTED),
    DOWN_SHELVES(1,"下架申请","您有来自于%s的%s条商品待下架确认",false ,MsgGroupEnum.LISTED),
    UP_SHELVES_ACCEPT(2,"上架申请，同意上架","您有%s条商品上架确认通过，已上架",false ,MsgGroupEnum.LISTED),
    UP_SHELVES_REJECT(3,"上架申请，驳回上架申请","您有%s条商品挂牌审核被%s-%s驳回",false ,MsgGroupEnum.LISTED),
    DOWN__SHELVES_ACCEPT(4,"下架申请，同意下架","您有%s条商品下架申请审核通过，已下架",false ,MsgGroupEnum.LISTED),
    DOWN_SHELVES_REJECT(5,"下架申请，驳回下架申请","您有%s条商品挂牌审核被%s-%s驳回",false ,MsgGroupEnum.LISTED),

    SUBMIT_ORDER(6,"提交订单","您有来自于%s-%s的<a class='c_blue' href='${href}'>%s</a>商城销售订单待接单确认",true ,MsgGroupEnum.RECEIVE_ORDER),
    ORDER_ACCEPT(7,"确认接单","您的<a class='c_blue' href='${href}'>%s</a>商城采购订单已被%s确认接单",true ,MsgGroupEnum.RECEIVE_ORDER),
    ORDER_REJECT(8,"审批驳回","您的<a class='c_blue' href='${href}'>%s</a>商城采购订单被%s-%s审批驳回",true ,MsgGroupEnum.RECEIVE_ORDER);


    private int typeCode;
    private String typeName;
    private String formatStr;
    private boolean needUrl;
    private MsgGroupEnum group;
    private String urlTag="${href}";

    public int getTypeCode() {
        return typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getFormatStr() {
        return formatStr;
    }

    public boolean isNeedUrl() {
        return needUrl;
    }

    public MsgGroupEnum getGroup() {
        return group;
    }

    public String getUrlTag() {
        return urlTag;
    }

    public String getContent (String url,String... s) {
        String str = String.format(formatStr, s);
        if(needUrl){
            str=str.replace(urlTag,url);
        }
        return str;
    }

    MsgTypeEnum(int typeCode, String typeName, String formatStr, boolean needUrl, MsgGroupEnum group) {
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.formatStr = formatStr;
        this.needUrl = needUrl;
        this.group = group;
    }

    /**
     * 消息大类
     */
    public enum MsgGroupEnum{
        LISTED("YGYG0001","挂牌提醒"),
        RECEIVE_ORDER("YGYG0002","接单提醒")
        ;

        private String groupCode;
        private String groupName;

        public String getGroupCode() {
            return groupCode;
        }

        public String getGroupName() {
            return groupName;
        }

        MsgGroupEnum(String groupCode, String groupName) {
            this.groupCode = groupCode;
            this.groupName = groupName;
        }
    }
}
