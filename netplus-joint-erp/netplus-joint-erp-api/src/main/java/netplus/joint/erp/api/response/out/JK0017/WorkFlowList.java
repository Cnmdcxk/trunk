package netplus.joint.erp.api.response.out.JK0017;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class WorkFlowList implements Serializable {

    private String workFlowType;
    private String remark;
}
