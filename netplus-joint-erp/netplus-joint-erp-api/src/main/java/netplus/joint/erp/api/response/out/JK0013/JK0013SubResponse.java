package netplus.joint.erp.api.response.out.JK0013;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0013SubResponse implements Serializable {

    private String createTime;
    private String from;
    private String originTo;
    private String smsMsgId;
    private String status;
}
