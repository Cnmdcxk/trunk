package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class BatchZkhBGoodPutOffRequest implements Serializable {

    private String goodNo;
    private String supplierNo;
    private String status;
    private BigDecimal qty;
}
