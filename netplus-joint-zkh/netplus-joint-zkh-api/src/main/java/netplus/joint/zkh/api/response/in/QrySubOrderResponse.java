package netplus.joint.zkh.api.response.in;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class QrySubOrderResponse implements Serializable {

    //电商订单号
    private String orderId;

    //物流状态
    private int state;

    //订单状态
    private int orderState;

    //确认状态
    private int submitState;

    //订单价格
    private BigDecimal orderPrice;

    //订单裸价格
    private BigDecimal orderNakedPrice;

    //订单税额
    private BigDecimal orderTaxPrice;

    //总运费
    private BigDecimal freight;

    //商品列表
    private List<Sku_entity> sku;

    //收货⼈详细地址
    private String address;

    //发票抬头
    private String companyName;

    //收货⼈详细地址
    private String customerDate;

    //配送预约⽇期
    private int promiseDate;

    //收货⼈
    private String name;

    //收货⼈⼿机号
    private String mobile;

    //采购组织名称
    private String purchaseOrg;

    //姓名或者客户系统⽤户名
    private String purchaseAccount;

    //下单⼈联系电话
    private String purchaseMobile;

    //收票⼈姓名
    private String invoiceName;

    //收票⼈电话
    private String invoicePhone;

    //收票⼈所在地址
    private String invoiceAddress;

    //备注（少于100字）
    private String remark;

}
