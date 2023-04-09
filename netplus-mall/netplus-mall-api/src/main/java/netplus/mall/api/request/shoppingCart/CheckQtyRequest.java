package netplus.mall.api.request.shoppingCart;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class CheckQtyRequest implements Serializable {
    private List<String> goodIdList;
}
