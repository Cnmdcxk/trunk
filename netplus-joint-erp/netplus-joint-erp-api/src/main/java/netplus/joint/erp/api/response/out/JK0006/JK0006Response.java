package netplus.joint.erp.api.response.out.JK0006;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JK0006Response {

    private String code;
    private String msg;
    private List<JK0006SubResponse> data;

}
