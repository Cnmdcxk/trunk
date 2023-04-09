package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateStaffStatusRequest {
    private String userNo;
    private String isActive;
}
