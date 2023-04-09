package netplus.joint.erp.api.response.out.JK0003;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JK0003SubResponse {

    private String bmbm_pk;//部门主键
    private String bmbh;//部门编号
    private String mc;//部门名称
    private String fgsbm_pk;//分公司主键
    private String fgsmc;//分公司名称
    private String db;//分公司编号
    private String sh;//税号
    private String kpgsdz;//开票公司地址
    private String kpgsdh;//开票公司电话
    private String khh;//开户行
    private String khzh;//开户账号
}
