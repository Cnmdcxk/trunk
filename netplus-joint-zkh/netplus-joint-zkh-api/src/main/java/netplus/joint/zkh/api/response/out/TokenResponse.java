package netplus.joint.zkh.api.response.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    private String access_token;    // Access_token值 将该值放到token字段，作
    private String refresh_token;   //  Refresh_token值
    private String time ;           // S 当前时间
    private int expires_in;         //  Int Y Access_token的过期时
    private int refresh_token_expires; //  Int N refresh_token的过期时间，毫秒级别
}
