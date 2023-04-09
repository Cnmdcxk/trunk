package netplus.mall.api.request.shoppingCart;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.vo.shoppingCart.Tbmqq433Vo;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class ExportErrInfoRequest implements Serializable {

    private List<Tbmqq433Vo> errInfo;
}
