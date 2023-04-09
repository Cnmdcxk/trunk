package netplus.joint.erp.api.request.out;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JK0034Request {

    private String appId;

    private String secret;

    private Long timestamp;

    private String scope = "app";

}
