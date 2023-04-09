package netplus.mall.api.vo.order;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class InvoiceInfoVo implements Serializable {
    private String tickreceiveraddr;
    private String tickreceivername;
    private String tickreceiverphone;
}
