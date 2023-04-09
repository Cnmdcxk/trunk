package netplus.joint.zkh.api.request.out;

import lombok.Getter;
import lombok.Setter;
import netplus.joint.zkh.api.request.BaseRequest;

import java.io.Serializable;

@Getter
@Setter
public class DeleteRequest extends BaseRequest {
    private String  id;

}
