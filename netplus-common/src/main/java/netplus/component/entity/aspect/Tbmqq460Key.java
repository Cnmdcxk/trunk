package netplus.component.entity.aspect;

public class Tbmqq460Key {
    private String reqid;

    private String callee;

    private String caller;

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid == null ? null : reqid.trim();
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee == null ? null : callee.trim();
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller == null ? null : caller.trim();
    }
}