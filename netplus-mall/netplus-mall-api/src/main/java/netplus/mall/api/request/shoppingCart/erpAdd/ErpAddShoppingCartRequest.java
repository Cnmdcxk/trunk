package netplus.mall.api.request.shoppingCart.erpAdd;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class ErpAddShoppingCartRequest implements Serializable {

    private String userNo;
    private List<ErpAddShoppingCartGoodsRequest> goodsList;

}
