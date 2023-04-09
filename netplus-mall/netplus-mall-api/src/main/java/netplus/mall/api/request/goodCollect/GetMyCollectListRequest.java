package netplus.mall.api.request.goodCollect;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class GetMyCollectListRequest extends RequestBase implements Serializable {
    private String searchKey;
    private String categoryNameList;
}
