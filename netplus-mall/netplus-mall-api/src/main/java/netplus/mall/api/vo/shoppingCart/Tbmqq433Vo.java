package netplus.mall.api.vo.shoppingCart;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq433;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class Tbmqq433Vo extends Tbmqq433 implements Serializable {

    private String goodStatus;
    private String message;
    private String buyernote;
    private List<String> goodIdList;
    private BigDecimal oldQty;


    private BigDecimal lowPrice;
    private String lowQtyUnit;
    private String cartGoodStatus;
    private String projectNameAndNo;

    private String qtyStr;

    //新协议价
    private BigDecimal futurePrice;
    //新协议生效时间
    private String futurePoPriceStartDate;
}

