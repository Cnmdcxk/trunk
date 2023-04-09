package netplus.mall.api.request.goodGroup;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class CreateGroupRequest implements Serializable {

    private List<String> goodIdList;
    private String appId;

}
