package netplus.joint.zkh.api.request.out;

import lombok.Data;
import netplus.joint.zkh.api.request.BaseRequest;

import java.io.Serializable;

@Data
public class QrySubOrderRequest extends BaseRequest implements Serializable {
    //电商订单号
    private String orderId;
}
