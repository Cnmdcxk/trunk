package netplus.mall.api.request;

import lombok.Data;

import java.util.List;

@Data
public class DeleteGoodsRequest {
    private String goodid;
    private String groupNo;
    private List<String> groupNoList;
}
