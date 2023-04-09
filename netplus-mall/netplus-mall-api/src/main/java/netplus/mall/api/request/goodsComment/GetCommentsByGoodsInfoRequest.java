package netplus.mall.api.request.goodsComment;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Getter
@Setter
public class GetCommentsByGoodsInfoRequest extends RequestBase implements Serializable {
    private String goodId;
    private String materialTm;
    private String materialNo;
    private Integer commentLevel;
}