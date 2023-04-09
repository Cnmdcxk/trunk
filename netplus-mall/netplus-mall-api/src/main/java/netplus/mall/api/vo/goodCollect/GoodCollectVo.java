package netplus.mall.api.vo.goodCollect;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq434;

import java.math.BigDecimal;

@Setter
@Getter
public class GoodCollectVo extends Tbmqq434 {

    private String status;
    private String isAddCart;
    private String isFailure;

    //新协议价
    private BigDecimal futurePrice;
    //新协议生效时间
    private String futurePoPriceStartDate;
}
