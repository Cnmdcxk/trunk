package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class UpdateStaffRoleRequest {
    private List<String> roleCodeList;
    private String userNo;
}
