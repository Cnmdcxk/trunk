package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.data.MinMax;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class GetOrderListRequest extends RequestBase implements Serializable {
    private String searchKey;
    private List<String> buyerNoList;
    private List<String> statusList;
    private List<String> brandList;
    private List<String> deptNoList;
    private List<String> supplierList;
    private List<String> lineList;
    private String status;
    private MinMax createTime;
    private String orderNo;
    private String isTimeOutOrder;
}
