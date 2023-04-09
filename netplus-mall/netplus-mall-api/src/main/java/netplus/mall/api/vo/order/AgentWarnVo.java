package netplus.mall.api.vo.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AgentWarnVo implements Serializable {

    private String agentno;
    private String supplierno;
    private String timeoutdate;
}
