package netplus.joint.zkh.api.request.out;

import lombok.Data;
import netplus.joint.zkh.api.request.BaseRequest;

import java.io.Serializable;

@Data
public class ConfirmPackageRequest extends BaseRequest implements Serializable {
    //ZKH发货单号
    private String packageId;
}
