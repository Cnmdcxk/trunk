package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class UpdateZkhGoodQtyRequest implements Serializable {

    private String goodId;
    private String supplierNo;
    private BigDecimal qty;
    private String updateDate;
    private String updateTime;
    private String updateUser;
}
