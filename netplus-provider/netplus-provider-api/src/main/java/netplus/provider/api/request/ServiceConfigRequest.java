package netplus.provider.api.request;

import lombok.Data;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Data
public class ServiceConfigRequest extends RequestBase implements Serializable {

    private String searchService;

}
