package netplus.captcha.service.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckCodeRequest {
    private String imgCode;
    private String imgType;
    private String uuid;
}
