package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String loginMethod;
    private String username;
    private String password;

    //验证失败次数
    private String errorTimes;
    private String imgCode;

    // 来源： PC， APP
    private String source;

    //模拟登录账号
    private String mockUsername;

    private String ticket;

    private String appId;
}
