package netplus.mall.api.request.goodCollect;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCollectRemarkRequest {

    private String goodId;
    private String remark;
}
