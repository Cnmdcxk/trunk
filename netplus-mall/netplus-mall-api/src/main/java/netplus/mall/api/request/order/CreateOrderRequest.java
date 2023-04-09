package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class CreateOrderRequest implements Serializable {

    private String deptLinePk;
    private String deptLineName;
    private String consigneeUserNo;
    private String consigneePhone;
    private String consigneeCode;
    private String sprCodeOne;
    private String sprCodeTwo;
    private String costDeptPK;
    private String costDeptName;
    private String costDeptNum;

    private List<CreateOrderDetailRequest> orderDetail;
}
