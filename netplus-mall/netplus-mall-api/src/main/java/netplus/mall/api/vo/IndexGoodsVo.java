package netplus.mall.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/*商城首页商品实体类*/
@Data
public class IndexGoodsVo implements Serializable {
    private String categoryname;

    private String categorypk;

    private List<GoodsVo> goodsVoList;

}