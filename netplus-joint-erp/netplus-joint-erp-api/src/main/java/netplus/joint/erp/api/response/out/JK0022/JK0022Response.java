package netplus.joint.erp.api.response.out.JK0022;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JK0022Response implements Serializable {

    private String code;
    private String message;
    private List<JK0022SubResponse> result;
}
