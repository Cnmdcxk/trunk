package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class BatchAuditRequest implements Serializable {

    private List<String> goodIdList;
    private String rejectReason;
    private String rejectOrAgree;
    private String auditRole; // A 采购组员审核，B组长审核
    private String biz; // A 上架确认 B 下架确认

}
