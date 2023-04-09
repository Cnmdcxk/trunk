package netplus.joint.erp.api.request.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JK0014Request implements Serializable {

    private String supplierPk;//				供应商主键
    private String supplierNo;//				供应商编号
    private String supplierName;//				供应商名称
    private String contacts;//				供应商联系人（有多个的给一条，也有空的情况）
    private String phone;//				供应商手机号

}
