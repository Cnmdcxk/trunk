package netplus.joint.zkh.api.response.in;

import lombok.Data;
import netplus.joint.zkh.api.response.in.DeliveryItems_entity;

import java.io.Serializable;
import java.util.List;

@Data
public class Delivery_entity implements Serializable {

    //发货单号
    private String packageId;

    //震坤⾏订单号
    private String orderId;

    //发货状态（0-待发货，1-已发货，2-签收，3-拒收）
    private Integer deliveryStatus;

    //物流单号
    private String deliveryCode;

    //发货时间
    private String deliverytime;

    //物流公司名称
    private String deliveryName;

    //发货单明细
    private List<DeliveryItems_entity> deliveryItems;

}
