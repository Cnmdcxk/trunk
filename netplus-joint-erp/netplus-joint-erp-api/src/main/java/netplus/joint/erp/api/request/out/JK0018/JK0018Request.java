package netplus.joint.erp.api.request.out.JK0018;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JK0018Request implements Serializable {

    private String businessId;
    private String createrCode;
    private List<JK0018SubRequest> nodeList;
}
