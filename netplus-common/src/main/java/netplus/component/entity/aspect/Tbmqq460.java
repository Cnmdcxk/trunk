package netplus.component.entity.aspect;

public class Tbmqq460 extends Tbmqq460Key {
    private String reqname;

    private String reqtime;

    private String requrl;

    private String resptime;

    private String status;

    private Short times;

    public String getReqname() {
        return reqname;
    }

    public void setReqname(String reqname) {
        this.reqname = reqname == null ? null : reqname.trim();
    }

    public String getReqtime() {
        return reqtime;
    }

    public void setReqtime(String reqtime) {
        this.reqtime = reqtime == null ? null : reqtime.trim();
    }

    public String getRequrl() {
        return requrl;
    }

    public void setRequrl(String requrl) {
        this.requrl = requrl == null ? null : requrl.trim();
    }

    public String getResptime() {
        return resptime;
    }

    public void setResptime(String resptime) {
        this.resptime = resptime == null ? null : resptime.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Short getTimes() {
        return times;
    }

    public void setTimes(Short times) {
        this.times = times;
    }
}