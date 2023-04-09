package netplus.joint.erp.api.request.in.JK0008;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class JK0008Request implements Serializable {

    private String ponoPk;//协议主键
    private String pono;//协议号
    private String startPopriceDatetime;//协议有效期起 (格式 yyyy-MM-dd HH:mm:ss)
    private String endPopriceDatetime;//协议有效期止 (格式 yyyy-MM-dd HH:mm:ss)
    private BigDecimal rate;//合同物资税率
    private String supplierPk;//供应商pk
    private String supplierNo;//供应商编号
    private String supplierName;//供应商名称
    private List<JK0008SubRequest> detail;
}
