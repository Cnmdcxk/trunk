package netplus.joint.erp.api.response.out.JK0033;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class JK0033Response implements Serializable {

    private String code;

    private String msg;

    private List<JK0033SubResponse> data;
}
