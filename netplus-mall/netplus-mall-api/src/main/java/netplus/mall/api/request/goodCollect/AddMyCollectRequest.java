package netplus.mall.api.request.goodCollect;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddMyCollectRequest {

    private String goodId;

    private String isDelCart; //Y 同时删除购物车，N 不删除购物车
}
