package netplus.joint.erp.api.response.out.JK0003;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class JK0003Response implements Serializable {
    private String code;
    private String msg;
    private int total;
    private List<JK0003SubResponse> data;
}
