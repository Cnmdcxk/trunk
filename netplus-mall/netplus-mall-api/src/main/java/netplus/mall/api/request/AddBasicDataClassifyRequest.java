package netplus.mall.api.request;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Setter
@Getter
public class AddBasicDataClassifyRequest extends RequestBase implements Serializable {
    private String categoryname;
    private String onelevelclaname;
    private String twolevelclaname;
    private String erpclaname;

}
