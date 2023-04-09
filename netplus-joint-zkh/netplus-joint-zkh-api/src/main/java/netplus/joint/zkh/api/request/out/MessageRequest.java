package netplus.joint.zkh.api.request.out;

import lombok.Getter;
import lombok.Setter;
import netplus.joint.zkh.api.request.BaseRequest;

@Getter
@Setter
public class MessageRequest extends BaseRequest {
    private int type;
}
