package netplus.joint.erp.api.response.out.JK0017;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JK0017SubResponse implements Serializable {

    private String processName;
    private String proposerName;
    private String businessSystem;
    private String businessId;
    private String createrCode;
    private List<WorkFlowList> workFlowList;
    private List<NodeList> nodeList;




}
