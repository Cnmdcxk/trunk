package netplus.mall.api.request;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.data.MinMax;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class GetBasicDataMaterialListRequest extends RequestBase implements Serializable {
    private MinMax updateDate;
    private String searchKey;
    private List<String> isactive;
    private String isthrplat;
    private List<String> twolevelclanameList;

}
