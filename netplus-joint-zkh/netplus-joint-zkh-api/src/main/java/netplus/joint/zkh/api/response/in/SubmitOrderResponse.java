package netplus.joint.zkh.api.response.in;

import lombok.Data;
import netplus.joint.zkh.api.response.BaseResponse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SubmitOrderResponse implements Serializable {

    //震坤⾏订单号
    private String orderId;

    //客户订单号
    private String thirdOrder;

    //总运费
    private BigDecimal freight;

    //商品信息
    private List<Sku_in> sku;

    //商品总价格
    private BigDecimal orderPrice;

    //订单裸价
    private BigDecimal orderNakedPrice;

    //订单税额
    private BigDecimal orderTaxPrice;

    //客户期望配送时间
    private String customerDate;

    //备注（少于100字）
    private String remark;

    //收货⼈详细地址
    private String address;

    //发票抬头
    private String companyName;
}
