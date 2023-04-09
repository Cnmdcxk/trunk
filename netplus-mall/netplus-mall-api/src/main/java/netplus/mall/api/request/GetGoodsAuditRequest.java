package netplus.mall.api.request;

import lombok.Data;
import netplus.component.entity.data.MinMax;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Data
public class GetGoodsAuditRequest extends RequestBase implements Serializable {
    private String searchKey;
    private MinMax createDate;
    private MinMax updateDate;
    private String categoryName;
    private String brand;
    private String suppliername;
    private String status;
    private String taxException;
    private String orderbyprice;
    private MinMax price;
    private String from;
}
