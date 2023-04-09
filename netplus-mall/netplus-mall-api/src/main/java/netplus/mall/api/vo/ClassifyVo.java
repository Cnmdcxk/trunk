package netplus.mall.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ClassifyVo implements Serializable {

    private String categoryname;

    private String onelevelclaname;

    private String twolevelclaname;

    private String erpclaname;

    private String updatedate;

    private String updatetime;
}
