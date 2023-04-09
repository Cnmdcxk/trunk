package netplus.provider.api.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class GetDialogInfoRequest extends RequestBase implements Serializable {
    private String searchKey;
    private Set<String> userNos;

    private String depno;   // 部门代码
    private String notUserNos; // 不在范围的用户id

}
