package netplus.joint.erp.api.request.out.JK0018;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0018SubRequest implements Serializable {
    private String nodeName;
    private String defaultApproveCode;
    private String defaultApproveName;
    private String chooseApproveCode;
    private String lineId;
    private String nodeIndex;
    private String chooseApproveName;
}
