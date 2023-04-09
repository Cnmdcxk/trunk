package netplus.iface.monitor.api.pojo.db;

public class Tbmqq460WithBLOBs extends Tbmqq460 {
    private String reqdata;

    private String respdata;

    public String getReqdata() {
        return reqdata;
    }

    public void setReqdata(String reqdata) {
        this.reqdata = reqdata == null ? null : reqdata.trim();
    }

    public String getRespdata() {
        return respdata;
    }

    public void setRespdata(String respdata) {
        this.respdata = respdata == null ? null : respdata.trim();
    }
}