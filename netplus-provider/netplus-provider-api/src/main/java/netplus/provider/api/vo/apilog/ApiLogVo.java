package netplus.provider.api.vo.apilog;

import lombok.Getter;
import lombok.Setter;
import netplus.provider.api.pojo.ygmalluser.ApiLog;

@Getter
@Setter
public class ApiLogVo extends ApiLog {

    private Integer indexNum;
    private String logTypeStr;
    private String createDateStr;
}
