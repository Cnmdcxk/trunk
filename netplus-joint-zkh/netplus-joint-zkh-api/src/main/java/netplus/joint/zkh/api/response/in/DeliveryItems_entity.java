package netplus.joint.zkh.api.response.in;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeliveryItems_entity implements Serializable {

    //商品编码
    private String skuId;

    //商品数量
    private int num;
}
