package netplus.mall.api.request.order;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class ChangeOrderDetailStatusRequest implements Serializable {

    private String newStatus;
    private String oldStatus;
    private String orderNo;
    private String orderDetailNo;
    private List<String> orderDetailNoList;
    private String supplierNo;
    private String opUser;
}
