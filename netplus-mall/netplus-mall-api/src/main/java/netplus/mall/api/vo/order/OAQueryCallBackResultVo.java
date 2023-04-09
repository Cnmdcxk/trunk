package netplus.mall.api.vo.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class OAQueryCallBackResultVo implements Serializable{

    private String code;
    private String message;
    private List<Map<String, Object>> result;
}
