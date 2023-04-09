package netplus.joint.zkh.api.request.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsInventory {
    // String Y 商品
    private String skuId;

    //int Y 数量
    private int num ;
}
