package netplus.mall.api.request.goodsComment;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GetCommentByOrderInfoRequest implements Serializable {

    private String orderNo;
    private String orderDetailNo;
}
