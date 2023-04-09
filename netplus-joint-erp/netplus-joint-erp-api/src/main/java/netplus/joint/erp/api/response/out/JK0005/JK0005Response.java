package netplus.joint.erp.api.response.out.JK0005;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class JK0005Response implements Serializable{
    private int total;//总条数
    private String code;
    private String msg;
    private List<JK0005SubResponse> rows;
}
