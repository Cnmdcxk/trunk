package netplus.messaging.api.vo;

public class MessagingVo {
    private String id;

    private String senduserno;

    private String receiveuserno;

    private Integer messagetype;

    private String messagegroup;

    private Integer isread;

    private String messagecontent;

    private String receivetime;

    private String url;

    private String messagetypeStr;

    public String getMessagetypeStr() {
        return messagetypeStr;
    }

    public void setMessagetypeStr(String messagetypeStr) {
        this.messagetypeStr = messagetypeStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenduserno() {
        return senduserno;
    }

    public void setSenduserno(String senduserno) {
        this.senduserno = senduserno;
    }

    public String getReceiveuserno() {
        return receiveuserno;
    }

    public void setReceiveuserno(String receiveuserno) {
        this.receiveuserno = receiveuserno;
    }

    public Integer getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(Integer messagetype) {
        this.messagetype = messagetype;
    }

    public String getMessagegroup() {
        return messagegroup;
    }

    public void setMessagegroup(String messagegroup) {
        this.messagegroup = messagegroup;
    }

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }

    public String getMessagecontent() {
        return messagecontent;
    }

    public void setMessagecontent(String messagecontent) {
        this.messagecontent = messagecontent;
    }

    public String getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(String receivetime) {
        this.receivetime = receivetime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}