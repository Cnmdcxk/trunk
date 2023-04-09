package netplus.mall.api.request.shoppingCart;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class AddShoppingCartRequest implements Serializable {

    private String goodId;
    private BigDecimal qty;
    private String isDefaultAdd; //加入购物车是否默认数量 Y是，N否
    private String isAddUp; //加入购物车数量是否累加 Y是，N否

}
