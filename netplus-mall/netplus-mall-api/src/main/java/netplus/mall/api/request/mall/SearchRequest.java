package netplus.mall.api.request.mall;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import netplus.component.entity.data.MinMax;
import netplus.component.entity.request.RequestBase;

import java.util.List;

@Setter
@Getter
@ToString
public class SearchRequest extends RequestBase {

    private List<String> twoLevelClaNameList;
    private List<String> categoryNameList;
    private List<String> onelevelClaNameList;
    private List<String> productNameList;
    private List<String> brandList;
    private MinMax price;
    private String groupNo;
    private String excludeGoodId;
    private String searchKey;

}
