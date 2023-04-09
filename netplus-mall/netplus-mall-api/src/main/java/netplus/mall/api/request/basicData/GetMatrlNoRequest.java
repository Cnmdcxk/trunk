package netplus.mall.api.request.basicData;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetMatrlNoRequest implements Serializable {


    private String supplierNo;
    private String thrplatGoodNo;
}
