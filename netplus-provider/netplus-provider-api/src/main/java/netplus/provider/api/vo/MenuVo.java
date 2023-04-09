package netplus.provider.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class MenuVo implements Serializable {

    private String code;
    private String name;
    private String url;
    private String icon;

    private List<MenuVo> childmenulist;
}
