package netplus.joint.zkh.api.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsStatusVo {

    //商品编号
    private String sku;

    //状态
    private int state; //1：上架 0：下架

}
