package netplus.joint.erp.api.response.out.JK0035;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JK0035Response {

    private String error;
    private int errorCode;
    private boolean success;
    private Long timestamp;

    private Data data;

}
