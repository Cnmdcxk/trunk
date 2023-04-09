package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetGoodBySupplierNoRequest implements Serializable {

    private String supplierNo;
}
