package netplus.mall.api.vo;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Goodscomment;
import netplus.mall.api.pojo.ygmalluser.Goodscommentimg;

import java.util.List;

@Getter
@Setter
public class GoodsCommentVo extends Goodscomment {

    /**
     * 评价图片
     */
    List<String> images;
}
