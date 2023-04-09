package netplus.joint.erp.api.request.out;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JK0035Request {

    private String accessToken;

    private String appid;

    private String ticket;
}
