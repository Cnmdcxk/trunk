package netplus.joint.erp.api.request.in.JK0008;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class JK0008SubRequest {
    private String wzmcbmPk;//物料id
    private String wztm;//物料条码
    private String wzbm;//物料编码
    private String wzmc;//物料名称
    private String ggxh;//物料规格型号
    private String jldw;//物料计量单位
    private String wzcgr;//物资采购人
    private BigDecimal price;//协议含税单价
    private String hasPic;//物资是否含图纸标记
    private BigDecimal jhzq;//交货周期
    private String wzcgrgh;//物资采购人工号
    private String wzcgrlxfs;//物资采购人联系方式
}
