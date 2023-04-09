package netplus.joint.zkh.api.request.out;

import lombok.Data;
import netplus.joint.zkh.api.request.BaseRequest;

import java.io.Serializable;

@Data
public class OrderTrackRequest extends BaseRequest implements Serializable {

    //查询发货单单号
    private String packageId;
}
