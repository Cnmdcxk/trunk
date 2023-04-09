package netplus.mall.api.vo.order;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq440;

@Getter
@Setter
public class PurchaseOrderExportVo extends Tbmqq440 {

    private String statusName;

    private String createTimeStr;
}
