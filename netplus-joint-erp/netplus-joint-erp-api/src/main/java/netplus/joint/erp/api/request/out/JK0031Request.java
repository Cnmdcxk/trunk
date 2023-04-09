package netplus.joint.erp.api.request.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0031Request implements Serializable {

    private String bmbh;//部门编号
    private String mc;//部门名称
}
