package netplus.mall.api.request;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Setter
@Getter
public class GoodsPictureListRequest extends RequestBase implements Serializable {

    private String goodId;
    private String supplierNo;
}
