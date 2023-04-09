package netplus.joint.erp.api.response.in;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JK0026Response implements Serializable {
    private Integer invalidNum;
    private Integer addNum;
    private Integer existNum;
}
