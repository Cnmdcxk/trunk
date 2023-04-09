package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UpdateOrderDetailInfoRequest {

    List<UpdateOrderDetailInfoSubRequest> subRequestList;
}
