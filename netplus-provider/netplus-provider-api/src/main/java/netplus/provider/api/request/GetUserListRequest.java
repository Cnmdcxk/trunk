package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class GetUserListRequest implements Serializable {
    private Set<String> userNos;
    private String deptno;
    private String postno;
}
