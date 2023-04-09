package netplus.mall.api.request;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.data.MinMax;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Setter
@Getter
public class GetGoodsGroupByIdRequest extends RequestBase implements Serializable {
    private String goodsId;
    private String searchKey;
}
