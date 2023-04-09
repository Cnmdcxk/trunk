package netplus.joint.erp.api.response.out.JK0010;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class JK0010SubResponse implements Serializable {

    private String wzmcbm_pk;   //物料主键
    private String wzbm;        //物料编号
    private String wzmc;        //物料名称
    private BigDecimal kcsl;        //库存数量
    private BigDecimal kcslsx;      //物资数量上限
    private BigDecimal jhsl;        //下单数量
    private String sfckc;       //是否超库存 1,0


    private BigDecimal remainQty;
}
