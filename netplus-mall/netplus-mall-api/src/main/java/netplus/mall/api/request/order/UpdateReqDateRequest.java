package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class UpdateReqDateRequest implements Serializable {

    private String orderDetailNo;
    private String orderNo;
    private String reqDeliDate;
}
