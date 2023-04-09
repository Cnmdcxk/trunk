package netplus.joint.zkh.api.response.in;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PackageListResponse implements Serializable {

    //发货单集合信息
    private List<Delivery_entity> deliveryList;
}
