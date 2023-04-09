package netplus.component.entity.data;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class KeyValue implements Serializable {

    private String key;
    private String value;

    // 统计
    private String total;
}
