package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetUserRoleListRequest implements Serializable {

    private String userNo;
}
