package netplus.joint.erp.api.request.in;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0023Request implements Serializable {
    private String scddmxbm_pk; //订单明细id
}
