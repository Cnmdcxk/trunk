package netplus.joint.erp.api.request.out.JK0010;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class JK0010SubRequest implements Serializable {

    private String wzmcbm_pk;
    private BigDecimal jhsl;
}
