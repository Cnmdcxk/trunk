package netplus.joint.zkh.api.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitOrderVo {

    private String orderId;// String Y 震坤⾏订单号

    private String thirdOrder;// String Y 客户订单号

//    private BigDecimal freight;// Double Y 总运费 总运费 = 基础运费 + 总的超重偏远附加运费，保留2位⼩数
//
//    private List<Sku> sku;// sku_entity[] Y 商品信息
//
//    private BigDecimal orderPrice;// Double Y 商品总价格 保留2位⼩数
//
//    private BigDecimal orderNakedPrice;// Double N 订单裸价 保留2位⼩数
//
//    private BigDecimal orderTaxPrice;// Double N 订单税额 保留2位⼩数
//
//    private String customerDate;// String N 客户期望配送时间 yyyy-MM-dd HH:mm:ss
//
//    private String remark;// String N 备注（少于100字）
//
//    private String address;// Stirng N 收货⼈详细地址
//
//    private String companyName;// String N 发票抬头
}
