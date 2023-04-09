package netplus.iface.monitor.api.vo;

import lombok.Data;
import netplus.iface.monitor.api.pojo.db.Tbmqq460;

import java.io.Serializable;

@Data
public class Tbmqq460Vo extends Tbmqq460 implements Serializable {
    private String reqdata;
    private String respdata;
}
