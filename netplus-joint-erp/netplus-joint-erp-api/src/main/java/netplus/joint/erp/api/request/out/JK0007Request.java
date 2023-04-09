package netplus.joint.erp.api.request.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0007Request  implements Serializable {
    private int pageIndex;
    private int pageSize;
}
