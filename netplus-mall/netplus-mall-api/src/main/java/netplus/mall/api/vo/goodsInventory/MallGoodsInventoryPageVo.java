package netplus.mall.api.vo.goodsInventory;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class MallGoodsInventoryPageVo implements Serializable {

    private String orderNo;
    private String orderDetailNo;
    private String matrlTm;
    private BigDecimal qty;
    private String createDate;
    private String createTime;
    private String statusName;
    private String supplierNameStr;
}
