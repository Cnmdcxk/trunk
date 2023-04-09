package netplus.joint.erp.api.response.in;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JK0014Response implements Serializable {

    private List<String> supplierPk;
}
