package netplus.joint.zkh.api.request.out;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Sku_out {

    //商品编号
    private String skuId;

    //商品数量
    private int num;

    //含税协议价格
    private BigDecimal price;

    //未税协议价格
    private BigDecimal nakedPrice;

    //客户物料号
    private String thirdSkuId;

    //客户物料名称
    private String thirdSkuName;

    //商品原价
    private BigDecimal originalPrice;
}
