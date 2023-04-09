package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AddShoppingCartFromOrderRequest {

    private String orderNo;
    private List<String> orderDetailNo;
}
