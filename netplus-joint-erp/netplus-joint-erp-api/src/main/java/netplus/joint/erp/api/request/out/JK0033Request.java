package netplus.joint.erp.api.request.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JK0033Request implements Serializable {

    private List<String> wzmcbm_pk_list;//物料ID列表
}
