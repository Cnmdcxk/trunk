package netplus.joint.erp.api.request.in.JK0026;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class JK0026Request implements Serializable {

    private String userNo;
    private List<JK0026GoodsRequest> goodsList;

}
