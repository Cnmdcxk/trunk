package netplus.mall.api.request.goodsInventory;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.request.RequestBase;

@Getter
@Setter
public class MallGoodsInventoryPageRequest extends RequestBase {

    private String matrlId;
}
