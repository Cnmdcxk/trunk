package netplus.joint.erp.api.response.in;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0025Response implements Serializable {
    private String redirectUrl;
    private String authToken;
    private String code;
    private String message;
}
