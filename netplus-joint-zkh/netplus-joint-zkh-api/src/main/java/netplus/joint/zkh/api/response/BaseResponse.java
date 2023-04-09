package netplus.joint.zkh.api.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class BaseResponse<T> implements Serializable {
    private T result;
    private String resultCode;
    private String resultMessage;
    private Boolean success;
}
