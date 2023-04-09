package netplus.provider.api.vo.apilog;

import lombok.Getter;
import lombok.Setter;
import netplus.provider.api.pojo.ygmalluser.SysLog;

@Getter
@Setter
public class SysLogVo extends SysLog {
    private Integer indexNum;
    private String logTypeStr;
    private String enterTimeStr;
    private String outTimeStr;
}
