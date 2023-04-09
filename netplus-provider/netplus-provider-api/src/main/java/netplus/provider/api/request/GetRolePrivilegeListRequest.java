package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetRolePrivilegeListRequest implements Serializable {

    private String rolecode;
}
