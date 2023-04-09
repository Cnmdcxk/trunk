package netplus.mall.api.request.goodsInventory;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.request.RequestBase;


/**
 * 分页查询商品库存请求
 * @author qinyi
 */
@Getter
@Setter
public class GoodsInventoryPageRequest extends RequestBase {

    private String searchKey;
}
