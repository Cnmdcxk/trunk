package netplus.mall.api.request.shoppingCart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BatchUpdateProjectRequest {
    private List<String> goodIdList;
    private String projectName;
    private String projectNo;
}
