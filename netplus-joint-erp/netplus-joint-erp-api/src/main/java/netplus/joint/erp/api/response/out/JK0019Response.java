package netplus.joint.erp.api.response.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0019Response implements Serializable {

    private String code;
    private String message;
    private String result;
}
