package netplus.joint.zkh.api.response.in;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Sku_in implements Serializable {
    //商品编号
    private String skuId;

    //商品数量
    private int num;

    //含税协议价格
    private BigDecimal price;

    //商品名称
    private String name;

    //税率
    private int tax;

    //不含税协议价格
    private BigDecimal nakedPrice;

    //客户物料号
    private String thirdSkuId;

    //客户物料名称
    private String thirdSkuName;

    //配送预约⽇期
    private int promiseDate;
}
