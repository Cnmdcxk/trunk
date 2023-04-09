package netplus.joint.zkh.api.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoodsInventoryVo {

    private String areaId; //": null,
    private List<String> deliveryTimeDesc; //": [">0米 需订货 约11工作日发货；"],
    private int inStockQty; //": 0,
    private int remainNum; //": 0,
    private String skuId; //": "JU0457",
    private String stockStateDesc; //": "预定-预计需要11日发货",
    private int stockStateId; //": 36

}
