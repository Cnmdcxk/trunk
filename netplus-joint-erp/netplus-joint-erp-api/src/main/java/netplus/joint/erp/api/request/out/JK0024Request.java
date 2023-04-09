package netplus.joint.erp.api.request.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0024Request implements Serializable {

    private String loginName;
    private String password; //MD5加密
}
