package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class DeleteZkhGoodRequest implements Serializable {

    private String goodNo;
    private String supplierNo;
}
