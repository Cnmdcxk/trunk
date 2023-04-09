package netplus.mall.api.vo;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.vo.shoppingCart.Tbmqq433Vo;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class ShoppingCartVo implements Serializable {
    private String ponopk;
    private String supplierno;
    private String suppliername;

    private List<Tbmqq433Vo> tbmqq433VoList;
}
