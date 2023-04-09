package netplus.joint.zkh.api.request.out;

import lombok.Data;
import netplus.joint.zkh.api.request.BaseRequest;

import java.io.Serializable;

@Data
public class PackageRequest extends BaseRequest {

    //发货单号
    private String packageId;
}
