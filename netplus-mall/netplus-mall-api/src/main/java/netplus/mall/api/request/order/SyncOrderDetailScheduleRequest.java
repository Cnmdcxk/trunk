package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class SyncOrderDetailScheduleRequest extends RequestBase implements Serializable {
    private List<String> orderNoList;
    private List<String> orderDetailIdList;

}
