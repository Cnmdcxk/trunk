package netplus.joint.zkh.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class BaseRequest implements Serializable {
    private String token;
}
