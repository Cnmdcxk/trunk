package netplus.joint.erp.api.response.out.JK0017;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0017Response implements Serializable {

    private String code;
    private String message;
    private JK0017SubResponse result;

}
