package netplus.mall.api.vo.basicData;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CheckOrderConfigVo implements Serializable {

    private String deptNo;
    private String category;

}
