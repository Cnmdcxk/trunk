package netplus.joint.erp.api.request.in;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class JK0013Request implements Serializable {

    private List<String> receiver;//电话号码
    private String templateId;//模板ID
    private Map<String,String> templateParas;

}
