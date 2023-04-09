package netplus.mall.api.request.basicData;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetGoodsNoRequest implements Serializable {

    private String supplierNo;
    private String matrlno;
}
