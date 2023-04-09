package netplus.mall.api.vo.order;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class CreateOrderResultVo {

    private List<String> orderNoList = new ArrayList();

    private List<Tbmqq441Vo> tbmqq441VoList = new ArrayList();

    public void addOrderNo (List<String> orderNoList) {
        this.orderNoList.addAll(orderNoList);
    }

    public void addTbmqq441VoList (List<Tbmqq441Vo> tbmqq441VoList) {
        this.tbmqq441VoList.addAll(tbmqq441VoList);
    }
}
