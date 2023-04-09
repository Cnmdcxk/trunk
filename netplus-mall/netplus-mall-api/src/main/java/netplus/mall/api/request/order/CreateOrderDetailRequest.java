package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class CreateOrderDetailRequest implements Serializable {

    private String ponopk;
    private String buyerNote;
    private List<CreateOrderSubDetailRequest> goodList;

}
