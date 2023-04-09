package netplus.joint.erp.api.response.out.JK0015;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class JK0015Response {
    private String code;
    private String msg;
    private List<JK0015SubResponse> data;
}
