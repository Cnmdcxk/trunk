package netplus.joint.erp.api.response.out.JK0016;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JK0016SubResponse implements Serializable {

    private String bmbm_pk;
    private String bmmc;
    private String bmtxmc_dlmc;
    private List<JK0016SprOneResponse> sprone;
    private List<JK0016SprTwoResponse> sprtwo;
}
