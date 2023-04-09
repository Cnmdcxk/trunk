package netplus.joint.erp.api.response.out;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JK0009Response {

    private String msg;
    private String code;
    private List<JK0009SubResponse> data;

}
