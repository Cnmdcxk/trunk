package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckPhoneRequest {

    private String mobile;
    private String mobileCode;
}
