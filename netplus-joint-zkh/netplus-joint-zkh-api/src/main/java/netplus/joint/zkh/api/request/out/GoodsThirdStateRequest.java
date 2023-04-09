package netplus.joint.zkh.api.request.out;

import lombok.Getter;
import lombok.Setter;
import netplus.joint.zkh.api.request.BaseRequest;

import java.util.List;

@Getter
@Setter
public class GoodsThirdStateRequest extends BaseRequest {
    private List<SkuThirdState> skuThirdState;
}
