package netplus.mall.api.request.mall;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GetGoodsHistoryRequest implements Serializable {

    private String pono;
    private String matrltm;
    private String suppliername;
}
