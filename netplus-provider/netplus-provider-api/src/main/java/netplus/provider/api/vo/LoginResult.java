package netplus.provider.api.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResult {

    private String code;
    private String msg;
    private String authToken;

    private LoginUserBean loginUserInfo;
}
