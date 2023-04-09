package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class BatchUpdatePriceRequest implements Serializable {

    private List<String> goodIdList;
    private String addOrSub;
    private BigDecimal updatePriceRange;

}
