package netplus.mall.api.request;

import lombok.Data;
import netplus.component.entity.data.MinMax;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Data
public class GetGoodsDetailRequest extends RequestBase implements Serializable {
    private String goodsId;

    private String matrltm;
}
