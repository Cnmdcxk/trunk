package netplus.joint.zkh.api.request.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {
    private String client_id;          //对接账号 由电商事先分配
    private String  client_secret;    //对接账号的密码 由电商事先分配
    private String  timestamp;        //当前调⽤时间 格式为“2014-01-01 01:01:01”
    private String  username;         //⽤户名
    private String  password;         //⽤户密码 md5加密后的字符串
}
