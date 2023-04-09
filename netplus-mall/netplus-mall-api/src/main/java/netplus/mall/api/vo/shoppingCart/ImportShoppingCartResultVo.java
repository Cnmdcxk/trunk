package netplus.mall.api.vo.shoppingCart;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class ImportShoppingCartResultVo implements Serializable {

    private int totalCount;
    private int errCount;
    private int successCount;
    private List<Tbmqq433Vo> errInfo;
}
