package netplus.mall.api.vo.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ConsigneeInfoVo implements Serializable {

    private String code;

    private String province;

    private String city;

    private String consigneename;

    private String consigneephone;

    private String consigneeaddr;

}
