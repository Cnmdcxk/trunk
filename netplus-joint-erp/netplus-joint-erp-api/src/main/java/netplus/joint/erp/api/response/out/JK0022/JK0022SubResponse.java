package netplus.joint.erp.api.response.out.JK0022;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0022SubResponse implements Serializable {
    private String approveName;
    private String approveResult;
    private String approveOpinion;
    private String approveTime;
}
