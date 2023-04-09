package netplus.mall.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/*商城首页目录实体类*/
@Data
public class MenuGoodsVo implements Serializable {
    private String categoryname;
    private String icon;

    private List<onelevelcla> onelevelcla;


}