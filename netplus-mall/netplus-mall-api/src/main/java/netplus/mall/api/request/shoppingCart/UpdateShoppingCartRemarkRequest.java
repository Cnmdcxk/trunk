package netplus.mall.api.request.shoppingCart;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UpdateShoppingCartRemarkRequest  implements Serializable {

    private String goodId;
    private String remark;
}
