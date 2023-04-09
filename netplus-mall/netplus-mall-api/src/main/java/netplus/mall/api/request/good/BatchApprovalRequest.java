package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class BatchApprovalRequest implements Serializable {

    private List<String> goodIdList;
}
