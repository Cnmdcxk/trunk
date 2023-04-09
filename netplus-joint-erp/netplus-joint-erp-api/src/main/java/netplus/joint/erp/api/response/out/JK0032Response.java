package netplus.joint.erp.api.response.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0032Response implements Serializable {
    private String msg;
    private String code;
}
