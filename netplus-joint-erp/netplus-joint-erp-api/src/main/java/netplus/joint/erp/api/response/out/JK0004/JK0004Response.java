package netplus.joint.erp.api.response.out.JK0004;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JK0004Response implements Serializable {

    private String code;
    private String msg;
    private List<JK0004SubResponse> data;
}
