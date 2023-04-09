package netplus.mall.api.request.basicData;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetCategoryNameListRequest implements Serializable {
    private String categoryName;
    private String oneLevelClaName;
    private String twoLevelClaName;
}
