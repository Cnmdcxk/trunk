package netplus.joint.erp.api.request.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0016Request implements Serializable {

    private String bmbm_pk;
    private String bmtxbm_pk;
}
