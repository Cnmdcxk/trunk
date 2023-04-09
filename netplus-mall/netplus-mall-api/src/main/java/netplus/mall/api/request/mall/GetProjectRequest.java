package netplus.mall.api.request.mall;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Setter
@Getter
public class GetProjectRequest extends RequestBase implements Serializable {

    private String searchKey;
}
