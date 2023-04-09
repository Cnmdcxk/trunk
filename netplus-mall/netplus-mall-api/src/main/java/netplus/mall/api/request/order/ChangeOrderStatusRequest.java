package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ChangeOrderStatusRequest  implements Serializable {

    private String newStatus;
    private String oldStatus;
    private String orderNo;
    private String supplierNo;
    private String opUser; //操作人代码
}
