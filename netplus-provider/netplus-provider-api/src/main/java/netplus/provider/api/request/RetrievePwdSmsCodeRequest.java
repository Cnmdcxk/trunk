package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetrievePwdSmsCodeRequest {

    private String mobile;
    private String workType;
    private String mobileCode;
}
