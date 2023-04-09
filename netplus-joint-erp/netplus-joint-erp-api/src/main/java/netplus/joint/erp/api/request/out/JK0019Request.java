package netplus.joint.erp.api.request.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0019Request implements Serializable {

    private String businessId;
    private String businessNo;
    private String businessQueryUrl;
    private String businessUpdateUrl;
    private String approvalCategory;
    private String approvalTitle;
    private String creatorCode;
    private String abstracts;
    private String workFlowType;

}
