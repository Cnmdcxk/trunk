package netplus.mall.api.vo.iface;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class GoodLatestPriceVo implements Serializable {

    private String matrlIdOrSku;
    private BigDecimal price;
}
