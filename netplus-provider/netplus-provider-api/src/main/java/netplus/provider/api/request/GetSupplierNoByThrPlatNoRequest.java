package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class GetSupplierNoByThrPlatNoRequest implements Serializable {

    private String thrPlatCode;
    private List<String> thrPlatSupplierNoList;
}
