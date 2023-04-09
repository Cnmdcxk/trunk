package netplus.mall.api.request.mall;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class GetCountPonoInfoRequest extends RequestBase implements Serializable {
    private List<String> statusList; //10, 15 未上架
    private String isOverdue; // Y 临超期合同
    private String pono; // 获取协议明细时传协议号
}
