package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetSupplierGoodEditInfoRequest implements Serializable {

    private String goodId;
}
