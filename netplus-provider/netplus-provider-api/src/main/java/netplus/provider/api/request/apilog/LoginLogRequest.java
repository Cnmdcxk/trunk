package netplus.provider.api.request.apilog;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Getter
@Setter
public class LoginLogRequest extends RequestBase implements Serializable {

    private String searchKey;
}
