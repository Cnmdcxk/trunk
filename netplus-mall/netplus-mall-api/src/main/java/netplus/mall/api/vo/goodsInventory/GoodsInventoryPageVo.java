package netplus.mall.api.vo.goodsInventory;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 商品库存分页查询VO
 * @author qinyi
 */
@Getter
@Setter
public class GoodsInventoryPageVo implements Serializable {

    /**
     * 物料ID
     */
    private String matrlId;

    /**
     * 物料条码
     */
    private String matrlTm;

    /**
     * 总库存
     */
    private BigDecimal totalInventory;

    /**
     * 商城库存
     */
    private BigDecimal mallInventory;

    /**
     * 仓库库存
     */
    private BigDecimal warehouseInventory;

    /**
     * 仓库库存上限
     */
    private BigDecimal warehouseInventoryMax;

}
