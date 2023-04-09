package netplus.joint.erp.api.request.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class JK0001Request implements Serializable {

    private String username;//工号
    private String password;//密码
    private String SWCODE;//系统别 (ygck,写死)， 需要提供正式环境账号
}
