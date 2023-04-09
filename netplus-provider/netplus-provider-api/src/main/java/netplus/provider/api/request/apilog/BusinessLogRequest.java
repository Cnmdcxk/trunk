package netplus.provider.api.request.apilog;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.data.MinMax;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Getter
@Setter
public class BusinessLogRequest extends RequestBase implements Serializable {

    private MinMax createDate;
    private String logType;
    private String searchKey;
}
