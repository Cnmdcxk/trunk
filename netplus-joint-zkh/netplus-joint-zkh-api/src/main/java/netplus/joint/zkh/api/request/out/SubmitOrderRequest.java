package netplus.joint.zkh.api.request.out;

import lombok.Data;
import netplus.joint.zkh.api.request.BaseRequest;

import java.io.Serializable;
import java.util.List;

@Data
public class SubmitOrderRequest extends BaseRequest {

    //订单号 客户商城系统订单号
    private String thirdOrder;

    //商品信息
    private List<Sku_out> sku;

    //发票信息 包含发票抬头、开票类型等
    private InvoiceInfo invoiceInfo;

    //姓名或者客户 系统⽤户名  下单⼈（或者采购⼈）姓名；请务必填 写；否则使⽤默认对接账号
    private String purchaseAccount;

    //下单⼈联系电话 下单⼈（或者采购⼈）⼿机号；请务必 填写；否则使⽤默认对接账号
    private String purchaseMobile;

    //收货⼈
    private String name;

    //收货⼈详细地址
    private String address;

    //收货⼈⼿机号
    private String mobile;

    //收货⼈座机号
    private String phone;

    //收货⼈邮箱
    private String email;

    //收货⼈的区域 地址信息
    private Region region;

    //备注（少于100字）
    private String remark;

    //订单类型
    private int orderType;

    //是否整单配送
    private int isBatching;

    //是否⾃动确认订单
    private Boolean autoConfirm;

    //配送预约⽇期
    private int promiseDate;

    //客户期望配送时间
    private String customerDate;

    //收票⼈姓名
    private String invoiceName;

    //收票⼈电话
    private String invoicePhone;

    //收票⼈所在地址
    private String invoiceAddress;

}
