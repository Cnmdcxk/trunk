package netplus.mall.api.request.shoppingCart;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class GetMyShoppingCartRequest implements Serializable {

    private List<String> categoryList;
    private List<String> brandList;
    private String searchKey;
    private List<String> goodIdList;
}
