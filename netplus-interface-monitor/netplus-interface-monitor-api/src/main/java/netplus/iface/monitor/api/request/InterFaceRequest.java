package netplus.iface.monitor.api.request;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.data.MinMax;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
public class InterFaceRequest extends RequestBase implements Serializable {
    private String searchKey;
    private List<String> calleeList;
    private List<String> callerList;
    private List<String> reqnameList;
    private List<String> statusList;
    private MinMax reqTime;
}
