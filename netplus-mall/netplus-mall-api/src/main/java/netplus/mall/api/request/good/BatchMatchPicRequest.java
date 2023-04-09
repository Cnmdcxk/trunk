package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class BatchMatchPicRequest implements Serializable {

    private List<String> goodIdList;
    private String cover; //1覆盖，0不覆盖

}
