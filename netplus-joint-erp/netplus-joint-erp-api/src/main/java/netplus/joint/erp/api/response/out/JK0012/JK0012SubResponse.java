package netplus.joint.erp.api.response.out.JK0012;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class JK0012SubResponse {

    private String scddmxbm_pk;//		String	50	是		下单明细行号或id，必须唯一
    private BigDecimal sgsl;//		String	50	是		送货数量 （发货数量）
    private BigDecimal rksl;//		String	50	是		入库数量 （收货数量）
    private BigDecimal jssl;//		String	50	是		结算数量   (开票数量)
    private String zt; //状态 是否完结 1完结，0未完成
    private String kddh; //快递单号
}
