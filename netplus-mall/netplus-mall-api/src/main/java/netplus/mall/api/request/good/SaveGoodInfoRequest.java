package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq435;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class SaveGoodInfoRequest implements Serializable {

    private String goodId;
    private BigDecimal price;
    private int packThick;
    private int packWidth;
    private int packHeight;
    private String packUnit;
    private String content;
    private BigDecimal minBuyQty;
    private String isIntTimePurchase;
    private List<Tbmqq435> mainPicList;
}
