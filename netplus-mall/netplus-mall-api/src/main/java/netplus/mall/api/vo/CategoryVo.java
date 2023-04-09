package netplus.mall.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class CategoryVo implements Serializable {

    private String categoryName;

    private String onelevelclaName;

    private String twolevelclaName;

    private String thrplatUnit;

}
