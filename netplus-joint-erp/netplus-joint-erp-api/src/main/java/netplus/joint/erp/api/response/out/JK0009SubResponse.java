package netplus.joint.erp.api.response.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class JK0009SubResponse implements Serializable {

    private String num; // 1 成功，0失败
    private BigDecimal jhsyje; //计划剩余金额（可以用使用的）
    private String ydjy; //预算类型
}
