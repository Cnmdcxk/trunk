package netplus.joint.zkh.api.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoodsImageVo {

    // String Y 商品编号
    private String sku;

    // skuPic_entity[] Y 商品图⽚
    private List<Images> skuPic;
}
