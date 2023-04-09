package netplus.joint.zkh.api.response.in;

import lombok.Data;

import java.io.Serializable;

@Data
public class SelfLogisticsInfo implements Serializable {
    //⻋辆物流轨迹URL
    private String logisticsUrl;
}
