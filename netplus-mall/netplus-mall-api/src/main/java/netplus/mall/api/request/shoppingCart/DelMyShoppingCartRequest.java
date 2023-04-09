package netplus.mall.api.request.shoppingCart;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class DelMyShoppingCartRequest implements Serializable {

    private List<String> goodIdList;
    private String operation; //B, 删除无效商品才需要此参数
}
