package netplus.mall.api.request.mall;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class GetCountGoodInfoRequest extends RequestBase implements Serializable {
    private List<String> statusList; //15待接单，25待交货
}
