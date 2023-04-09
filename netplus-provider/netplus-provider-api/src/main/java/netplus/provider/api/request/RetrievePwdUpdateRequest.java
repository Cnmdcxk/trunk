package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetrievePwdUpdateRequest {
    private String sessionId;
    private String password;
    private String confirmPassword;
}
