package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserPwdRequest {
    // 用户code
    private String userCode;
    // 历史密码
    private String password;
    // 新密码
    private String newPwd;

    private String confirmPwd;
}
