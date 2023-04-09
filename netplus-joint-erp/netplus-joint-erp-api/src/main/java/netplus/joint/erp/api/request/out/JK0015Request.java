package netplus.joint.erp.api.request.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JK0015Request implements Serializable {

    private String gypbm_pk;//				是		协议主键
    private List<String> wzmcbm_pk_list;//		array		是	['xxxx', 'xxxx']	物料id列表
}
