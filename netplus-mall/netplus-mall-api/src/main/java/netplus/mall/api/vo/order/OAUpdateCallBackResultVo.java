package netplus.mall.api.vo.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class OAUpdateCallBackResultVo implements Serializable {
    private String code;
    private String message;
    private String result;
}
