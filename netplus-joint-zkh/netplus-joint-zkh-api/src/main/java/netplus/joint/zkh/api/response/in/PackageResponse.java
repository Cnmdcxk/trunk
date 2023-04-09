package netplus.joint.zkh.api.response.in;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PackageResponse implements Serializable {

    //发货单号
    private String packageId;

    //震坤⾏订单号
    private String orderId;

    //物流公司物流单号
    private String deliveryCode;

    //发货时间
    private String deliverytime;

    //物流公司名称
    private String deliveryName;

    //发货单明细
    private List<DeliveryItems_entity> deliveryItems;
}
