package netplus.joint.erp.api.response.out.JK0005;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.AbstractQueue;

@Setter
@Getter
public class JK0005SubResponse implements Serializable {

    private String xmmc;//项目名称
    private String xmbh;//项目编号
    private String fyzc;//费用/资产标记（1表示资产 、0或2表示费用）
    private String glfl;//项目分类（0：工业项目、1：民用项目、2：信息化项目、3：科研项目、4：大修项目）
    private String yzdw_rs;//业主单位
}
