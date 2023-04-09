package netplus.mall.api.request.goodCollect;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DelMyCollectRequest {

    private List<String> goodIds;
}
