package netplus.mall.api.request.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ViewApproveProgressRequest implements Serializable {

    private String approveCode;
}
