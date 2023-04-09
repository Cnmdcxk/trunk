package netplus.joint.erp.api.response.out.JK0016;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JK0016Response implements Serializable {

    private String msg;
    private String code;
    private List<JK0016SubResponse> data;
}
