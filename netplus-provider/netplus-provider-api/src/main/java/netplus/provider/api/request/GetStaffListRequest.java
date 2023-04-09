package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.data.MinMax;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Setter
@Getter
public class GetStaffListRequest extends RequestBase implements Serializable {
    private String searchKey;
    private MinMax createDate;
    private String isRole;
    private String userType;

}
