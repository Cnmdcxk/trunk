package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class UpdateOrderDetailInfoSubRequest {

    private String erpOrderNo; //erp合同号（和平台订单号对应）
    private String erpOrderDetailNo; //erp合同子项号 （和平台订单明细号对应）
    private BigDecimal deliQty; //发货数量 (增量)
    private BigDecimal takeDeliQty; //收货数量 (增量)
    private BigDecimal invoiceQty; // 开票数量 (增量)
}
