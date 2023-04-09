package netplus.joint.erp.api.request.out;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class JK0005Request implements Serializable {

    private String xmmc;//项目名称
    private String xmbh;//项目编号
    private int pageSize;
    private int pageIndex;
}
