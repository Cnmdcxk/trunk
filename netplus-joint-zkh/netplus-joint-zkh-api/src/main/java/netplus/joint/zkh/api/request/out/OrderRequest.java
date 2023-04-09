package netplus.joint.zkh.api.request.out;

import lombok.Data;
import netplus.joint.zkh.api.request.BaseRequest;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderRequest extends BaseRequest {

    //震坤⾏订单号
    private String orderId;

    //客户参考号（或者叫执⾏单号）
    private String referenceNumber;

    //以数组⽅式提供明细信息
    private List<String> skuNoList;
}
