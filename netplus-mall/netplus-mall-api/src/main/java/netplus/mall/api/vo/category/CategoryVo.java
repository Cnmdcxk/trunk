package netplus.mall.api.vo.category;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class CategoryVo implements Serializable {

    private String name;
    private String icon;
    private String id;
    List<CategoryVo> children;
}
