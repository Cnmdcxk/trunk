package netplus.joint.erp.api.request.out.JK0011;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JK0011Request implements Serializable {

    private String ownercode;//		String	50	是		工号
    private String ownername;//		String	50	否		姓名
    private String bmbm_pk;//		String	50	是		部门主键
    private String bmbh;//		String	50	否		部门编号
    private String mc;//		String	200	否		部门名称
    private String gypbm_pk;//		String	50	是		框架协议主键
    private String hth;//	String	50	是		框架协议号
    private String orderno;//		String		是		商城订单号
    private String db;

    private String sprcodeone;
    private String sprnameone;
    private String sprcodetwo;
    private String sprnametwo;
    private String scddbm_pk;
    private String spjssj;
    private String xdsj;

    private List<JK0011SubRequest> detail;
}
