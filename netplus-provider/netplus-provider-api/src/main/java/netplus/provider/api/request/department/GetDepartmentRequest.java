package netplus.provider.api.request.department;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetDepartmentRequest implements Serializable {

    private String depno;
}
