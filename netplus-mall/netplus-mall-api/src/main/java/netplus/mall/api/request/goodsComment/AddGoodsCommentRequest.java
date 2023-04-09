package netplus.mall.api.request.goodsComment;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class AddGoodsCommentRequest  implements Serializable {

    private String orderNo;
    private String orderDetailNo;
    private Integer score;
    private String commentContent;
    private List<String> imgUrl;

}
