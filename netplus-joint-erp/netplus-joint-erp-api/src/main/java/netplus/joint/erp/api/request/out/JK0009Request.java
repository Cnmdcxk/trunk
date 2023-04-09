package netplus.joint.erp.api.request.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class JK0009Request implements Serializable {

    private String bmbm_pk;//部门主键
    private String bmtxbm_pk;//条线主键
    private BigDecimal zje;//本单总金额+供应商没接单的总金额（按部门条线计算，这里有逻辑的）
}
