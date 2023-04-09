package netplus.joint.erp.api.response.out.JK0028;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JK0028Response {

    private String code;
    private String message;
    private JK0028SubResponse result;
}
