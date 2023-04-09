package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetUserOwnRoleByUserNoRequest implements Serializable {

    private String userNo;
}
