package netplus.joint.erp.api.request.out.JK0032;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JK0032Request implements Serializable {
    private List<JK0032SubRequest> data;
}
