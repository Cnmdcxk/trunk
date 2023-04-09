package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class BatchChangeOrderStatusRequest implements Serializable {

    private List<ChangeOrderStatusRequest> changeOrderStatusRequestList;
}
