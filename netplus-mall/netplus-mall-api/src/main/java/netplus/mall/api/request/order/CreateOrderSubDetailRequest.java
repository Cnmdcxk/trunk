package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class CreateOrderSubDetailRequest implements Serializable {

    private String goodId;
    private String remark;
    private String remark2;
}
