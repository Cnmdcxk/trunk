package netplus.joint.zkh.api.request.out;

import lombok.Getter;
import lombok.Setter;
import netplus.joint.zkh.api.request.BaseRequest;

import java.util.List;

@Getter
@Setter
public class ServiceOrderRequest extends BaseRequest {

    // String Y 震坤⾏订 单号
    private String orderNumber;

    //Integer Y 售后
    private Integer serviceType;

    // detail_entity[] Y售后商品 集合
    private List<GoodsInventory> detail;

    // String N 售后申请原因
    private String description;

    // Integer N 售后⽅式 售后类型为退货时必传 1：供应商上⻔取件（地址订单收货⼈地址） ；2：客 户物流快递
    private Integer returnType;

    // String Y 申请⼈名 称
    private String applicant;

    //String Y 申请⼈电 话
    private String applicationPhone;

    // String N 申请时间
    private String applicationTime;
}
