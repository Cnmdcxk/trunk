package netplus.mall.api.request.basicData;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class GetMatrlByIdsAndSupplierNoRequest implements Serializable {
    private List<String> matrlIdList;
    private String supplierNo;
}
