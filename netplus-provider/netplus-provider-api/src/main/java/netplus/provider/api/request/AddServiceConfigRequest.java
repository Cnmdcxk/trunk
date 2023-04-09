package netplus.provider.api.request;

import lombok.Data;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Data
public class AddServiceConfigRequest extends RequestBase implements Serializable {

    private String code;

    private String name;

    private String val;

}
