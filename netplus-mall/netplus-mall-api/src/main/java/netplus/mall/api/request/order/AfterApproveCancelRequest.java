package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class AfterApproveCancelRequest implements Serializable {
    private String billCode;
}
