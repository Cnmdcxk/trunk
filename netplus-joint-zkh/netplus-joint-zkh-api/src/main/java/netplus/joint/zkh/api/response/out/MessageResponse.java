package netplus.joint.zkh.api.response.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MessageResponse<T> implements Serializable {

    private String id;
    private T result;
    private String time;
    private String type;

}
