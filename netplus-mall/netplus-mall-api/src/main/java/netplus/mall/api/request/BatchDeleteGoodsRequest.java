package netplus.mall.api.request;

import lombok.Data;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;

import java.util.List;

@Data
public class BatchDeleteGoodsRequest {
    private String groupno;
    private List<String> groupsNo;
}
