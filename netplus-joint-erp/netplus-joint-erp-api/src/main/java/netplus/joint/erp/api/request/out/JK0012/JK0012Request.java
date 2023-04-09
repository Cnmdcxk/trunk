package netplus.joint.erp.api.request.out.JK0012;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JK0012Request implements Serializable {
    List<String>  scddmxbm_pk_list;//		arrary	50	是	["xxx", "xxx", ....]	下单明细行号或id，必须唯一
}
