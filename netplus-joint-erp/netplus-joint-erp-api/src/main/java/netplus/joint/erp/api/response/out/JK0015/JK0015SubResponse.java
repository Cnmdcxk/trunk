package netplus.joint.erp.api.response.out.JK0015;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class JK0015SubResponse {
    private String gypbm_pk;//		string		是		协议主键
    private String wzmcbm_pk;//		string		是		物料id
    private String wztm;//		string		是		物料条码
    private BigDecimal hsdj;//		number		是		协议含税单价
}
