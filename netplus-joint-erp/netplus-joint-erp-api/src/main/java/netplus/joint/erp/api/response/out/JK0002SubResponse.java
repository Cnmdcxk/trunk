package netplus.joint.erp.api.response.out;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JK0002SubResponse {

    private String id;//用户id
    private String ownercode;//工号
    private String ownername;//姓名
    private String bmbm_pk;//部门主键
    private String bmbh;//部门编号
    private String mc;//部门名称
    private String fgsbm_pk;//分公司主键
    private String fgsmc;//分公司名称
    private String db;//分公司编号
    private String lxsj;//联系人手机（用于快递接收）
    private String scyhlb;//用户类别 1下单用户、2收货用户
}
