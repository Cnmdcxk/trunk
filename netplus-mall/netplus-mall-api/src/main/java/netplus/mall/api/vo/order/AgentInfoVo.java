package netplus.mall.api.vo.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AgentInfoVo implements Serializable {

    private String agentno;
    private String agentphone;
}
