package netplus.component.entity.iface;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class BaseRequest<T> implements Serializable {

    private String reqId;
    private String reqTime;
    private String reqtype;
    private String token;

    private String isRestart; //这个参数不对外部使用

    private T reqData;

}
