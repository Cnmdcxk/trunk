package netplus.joint.erp.api.response.out.JK0024;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0024Response implements Serializable {
    private String resultCode;
    private String resultMsg;
    private Object resultData;
}
