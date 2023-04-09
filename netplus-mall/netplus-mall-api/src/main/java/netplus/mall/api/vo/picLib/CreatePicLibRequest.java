package netplus.mall.api.vo.picLib;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
public class CreatePicLibRequest implements Serializable {

    @NotNull(message = "图片名称不允许为空", groups = {add.class})
    private String pictureName;
    private String pictureUrl;
    private String pictureType;

    /**
     * 参数校验分组：新增
     */
    public @interface add {
    }
}
