package netplus.joint.erp.api.response.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class JK0011Response implements Serializable {

    private String msg;
    private String code;
    private String contractNo;

}
