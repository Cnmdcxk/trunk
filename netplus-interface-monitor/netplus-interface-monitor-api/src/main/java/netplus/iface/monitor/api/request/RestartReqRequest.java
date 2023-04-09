package netplus.iface.monitor.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class RestartReqRequest implements Serializable {

    private String reqId;
    private String caller;
    private String callee;
}
