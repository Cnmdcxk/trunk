package netplus.joint.erp.api.response.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JK0002Response implements Serializable {

    private String code;
    private String msg;
    private List<JK0002SubResponse> data;

}
