package netplus.mall.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class PageRequest implements Serializable {

    private String page; //页面使用者的角色 B买方，S卖方
}
