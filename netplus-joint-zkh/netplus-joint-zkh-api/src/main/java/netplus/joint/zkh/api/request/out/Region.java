package netplus.joint.zkh.api.request.out;

import lombok.Data;

@Data
public class Region {

    //省
    private String province;

    //市
    private String city;

    //区/县
    private String county;

    //镇
    private String town;
}
