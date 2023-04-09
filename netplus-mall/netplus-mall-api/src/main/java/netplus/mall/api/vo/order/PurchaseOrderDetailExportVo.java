package netplus.mall.api.vo.order;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq441;

@Getter
@Setter
public class PurchaseOrderDetailExportVo extends Tbmqq441 {

    private String buyername;
    private String userno;
    private String username;
    private String deptname;
    private String buyernote;
    private String thrplatorderno;
    private String consigneename;
    private String consigneephone;
    private String consigneefulladdr;
    private String invoicetitle;
    private String receivingdate;
    private String receivingtime;
    private String bizcontact;
    private String bizcontactphone;



    private String bandAndProductName;
    private String taxStr;
    private String isNeedPicStr;
    private String createTimeStr;
    private String receivingTimeStr;
    private String leadTimeStr;
    private String statusName;

}
