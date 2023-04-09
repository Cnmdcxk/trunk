package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class UpdateZkhGoodPriceRequest implements Serializable {

    private String goodNo;
    private String supplierNo;
    private BigDecimal price;
    private BigDecimal noTaxPrice;
    private BigDecimal tax;
    private String updateDate;
    private String updateTime;
    private String updateUser;
}
