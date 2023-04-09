package netplus.mall.api.request.picLib;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class GetPicByMatrlTmAndSupplierNoRequest implements Serializable {

    private List<String> matrlTmList;
    private String supplierNo;
    private String deleted;

}
