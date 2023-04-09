package netplus.joint.erp.api.response.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0030Response implements Serializable {
    private int id;
    private String createDate;
    private String lastLoginDate;
    private String createdBy;
    private String modifiedBy;
    private String username;// 用户名
    private String sex;
    private String keShi;//当前组织
    private String name; //姓名
    private String enabled;

    private String error;
    private String error_description;
    private Integer status;
    private String message;
}
