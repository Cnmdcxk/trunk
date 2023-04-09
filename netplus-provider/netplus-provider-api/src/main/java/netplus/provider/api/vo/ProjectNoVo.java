package netplus.provider.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class ProjectNoVo implements Serializable {

    private String projectno;
    private String projectname;
    private String budgetno;
    private String budgetname;
    private BigDecimal availableamt;
}
