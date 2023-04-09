package netplus.joint.erp.api.response.out.JK0017;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class NodeList implements Serializable {

    private String lineId;
    private String nodeName;
    private String defaultApproveName;
    private String defaultApproveCode;
    private String chooseApproveName;
    private String chooseApproveCode;
    private String nodeIndex;
    private String unknowApprove;

}
