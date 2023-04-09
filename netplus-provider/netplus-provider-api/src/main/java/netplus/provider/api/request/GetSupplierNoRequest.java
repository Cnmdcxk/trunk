package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetSupplierNoRequest implements Serializable {
    private String supplierNo;
    private String compName;
}
