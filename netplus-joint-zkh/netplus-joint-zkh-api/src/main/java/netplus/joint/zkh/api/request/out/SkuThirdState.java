package netplus.joint.zkh.api.request.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkuThirdState {
    private String skuNo;
    private int state;
    private int approveState;
    private String prdAuditTime;
    private String updateTime;
}
