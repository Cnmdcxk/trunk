package netplus.mall.api.request.shoppingCart;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class UpdateShoppingCartRequest implements Serializable {

    private String goodId;
    private List<String> goodIdList;

    private String isQtyUp; //是否累加数量 Y是， N否
    private BigDecimal qty;

    private String projectNo;
    private String projectName;

    private String isNeedPic;

    private String deviceApplyPk;
    private String deviceApplyNo;

    private String deviceSimpleNo;
}
