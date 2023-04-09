package netplus.joint.erp.api.response.out.JK0010;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
public class JK0010Response implements Serializable {


    private String msg;
    private String code;
    private List<JK0010SubResponse> data;

}
