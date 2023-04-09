package netplus.joint.zkh.api.pojo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Sku {

    private String skuId;//String Y 商品编码

    private Integer num;// Int Y 商品数量

    private BigDecimal price;// Double Y 含税协议价格 保留2位⼩数

    private String name;// String Y 商品名称

    private Integer tax;// Int Y 税率

    private BigDecimal nakedPrice;// Double N 不含税协议价格不含税协议价格=含税协议单价/（1+税率%）,保留2位⼩数

    private String thirdSkuId;// String N 客户物料号

    private String thirdSkuName;//String N 客户物料名称

    private Integer promiseDate;// Int N 配送预约⽇期 1：⼯作⽇送货 2：任意时间送货
}
