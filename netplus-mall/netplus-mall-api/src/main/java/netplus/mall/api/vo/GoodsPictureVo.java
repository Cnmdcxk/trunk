package netplus.mall.api.vo;

import lombok.Data;
import netplus.mall.api.pojo.ygmalluser.Tbmqq435;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*商品图片实体类*/
@Data
public class GoodsPictureVo implements Serializable {
    private String createDate;
    private List<Tbmqq435> goodsPicture;

    public GoodsPictureVo(){
        this.goodsPicture = new ArrayList();
    }
}