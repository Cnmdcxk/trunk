package netplus.provider.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginUserBean  implements Serializable {

    private String companyName;
    private String username;
    private String mobile;
    private String userCode;
    private String companyCode;
    private String companyPk;
    private String role; // B 采购商，S 供货商
    private String deptNo;
    private String deptName;
    private String deptNum;
    private String bizContact;
    private String bizContactPhone;

}
