package netplus.joint.erp.api.request.in;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0025Request implements Serializable {
    private String code;
    private String source;
}
