package netplus.mall.api.request.iface;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Setter
@Getter
public class GetGoodLatestPriceRequest implements Serializable {

    private String ponopk;
    private Map<String, String> matrlIdAndSkuMap;
    private String isMallOutProvider; // 第三方供应商 Y是， N否

}
