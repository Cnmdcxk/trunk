package netplus.mall.api.vo.order;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq440;
import netplus.mall.api.pojo.ygmalluser.Tbmqq443;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class Tbmqq440Vo extends Tbmqq440 implements Serializable {

    private String statusName;
    private String payTypeName;
    private String payMethodName;
    private int statusSort;
    private List<Tbmqq441Vo> tbmqq441VoList;
    private int totalCount;
    private String agent;
    private String ordertypeName;
    private List<Tbmqq443> tbmqq443List;
    private String createUserName;
    private String isTimeOutOrder;
    private String approvedate;
    private String approvetime;
    private String sprcodeone;
    private String sprnameone;
    private String sprcodetwo;
    private String sprnametwo;
    private String phone;
}
