package netplus.joint.zkh.api.request.out;

import lombok.Data;
import netplus.joint.zkh.api.request.BaseRequest;

import java.io.Serializable;

@Data
public class SelectOrderIdByThirdOrderRequest extends BaseRequest implements Serializable {

    //商城订单号
    private String thirdOrder;
}
