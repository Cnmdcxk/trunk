package netplus.mall.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class onelevelcla  implements Serializable {
    private String onelevelcla;

    private List<twolevelcla> twolevelcla;
}
