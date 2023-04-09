package netplus.mall.api.vo.order;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq441;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class Tbmqq441Vo extends Tbmqq441 implements Serializable {

    private String statusName;
    private String orderStatus;
    private String oldStatus;
    private String erpOrderNo;
    private String taxStr;
    private String buyerno;
    private String userno;
    private String username;
    private String deptname;
    private String pname;
    private String remark;
    private String approvecode;
    private BigDecimal remainbudget;
    private String supplierno;
    private String suppliername;
    private Integer hasComment;
    private String isAddCart;
    private String isOutQty;
    private String isLowPrice;
    private String ponopk;
    private List<String> expressNoList;
    private BigDecimal remainQty;

    private String receivingdate;
    private String receivingtime;
    private String approvedate;
    private String approvetime;
    private String leaderTimeStr;
}
