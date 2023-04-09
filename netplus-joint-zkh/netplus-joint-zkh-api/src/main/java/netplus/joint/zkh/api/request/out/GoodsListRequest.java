package netplus.joint.zkh.api.request.out;

import lombok.Getter;
import lombok.Setter;
import netplus.joint.zkh.api.request.BaseRequest;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class GoodsListRequest extends BaseRequest {
    private List<String>  sku;
}
