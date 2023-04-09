package netplus.joint.zkh.api.request.out;

import lombok.Getter;
import lombok.Setter;
import netplus.joint.zkh.api.request.BaseRequest;

import java.util.List;

@Getter
@Setter
public class GoodsInventoryRequest extends BaseRequest {
    private List<GoodsInventory>  skuNums;

}
