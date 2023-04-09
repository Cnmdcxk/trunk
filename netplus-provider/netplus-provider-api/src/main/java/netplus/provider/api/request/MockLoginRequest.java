package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class MockLoginRequest implements Serializable {

    //当前登录人密码
    private String password;

    // 来源： PC， APP
    private String source;

    //模拟登录账号
    private String mockUsername;
}
