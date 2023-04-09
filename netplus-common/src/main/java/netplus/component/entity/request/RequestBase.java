package netplus.component.entity.request;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RequestBase implements Serializable {

    private Integer pageIndex = 1;
    private Integer pageSize = 10;
    private String orderBy;
    private boolean asc = false;
    private String page;
}
