package netplus.mall.api.request.goodCollect;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BatchAddMyCollectRequest {

    private List<String> goodIds;

    private String isDelCart; //Y 同时删除购物车，N 不删除购物车
}
