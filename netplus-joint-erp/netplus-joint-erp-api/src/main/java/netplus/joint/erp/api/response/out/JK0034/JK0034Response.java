package netplus.joint.erp.api.response.out.JK0034;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JK0034Response {


    private boolean success;

    private int errorCode;

    private String error;

    private Data data;
}
