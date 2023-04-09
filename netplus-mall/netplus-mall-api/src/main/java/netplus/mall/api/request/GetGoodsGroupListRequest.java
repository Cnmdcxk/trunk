package netplus.mall.api.request;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.data.MinMax;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class GetGoodsGroupListRequest extends RequestBase implements Serializable {

    private String searchKey;
    private MinMax createTime;
    private String categoryNameList;
    private List brandList;
    private String groupNo;


}
