package netplus.joint.zkh.api.response.in;

import lombok.Data;

import java.io.Serializable;

@Data
public class SelectOrderIdByThirdOrderResponse implements Serializable {

    //电商订单号
    private String result;
}
