package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class InvalidOrderRequest implements Serializable {
    private String orderNo;
    private String invalidReason;
}
