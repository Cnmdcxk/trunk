package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetOrderInfoRequest implements Serializable {

    private String orderNo;

    private Integer pageIndex;
    private Integer pageSize;
}
