package netplus.mall.api.vo;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class GoodsVo extends Tbmqq430 implements Serializable {

    private String statusName;
    private String isAddCart;
    private String referdeliverydateStr;
    private String isCollect;
    private String hasPic;
    private String content;
    private String oldStatus;
    private String statusList;
    private List<String> oldStatusList;
    private List<String> goodIdList;
    private List<String> goodNoList;
    private List<Tbmqq435Vo> goodPicList;

    private BigDecimal cartQty;
    private String reqDeliDate;

    //长协经办人名称
    private String username;

    private String taxStr;

    //长协合同号与项次号
    private String ponopoitemno;


    private String projectno;
    private String projectname;

    private int nums;

    private String auditusername;

    private String remark;

    private String saleQty;

    private String originpriceStr;

    private String priceStr;


    private String bandAndProductName;

    /**
     * 创建时间yyyy-MM-dd HH:mm:ss
     */
    private String createTimeStr;

    /**
     * 修改时间yyyy-MM-dd HH:mm:ss
     */
    private String updateTimeStr;

    //要求交货周期
    private String leadtimenumStr;

    private String popricestartdateStr;

    private String popricedateStr;

    private String deviceapplyno;
    private String deviceapplypk;
    private BigDecimal lowPrice;
    private String lowQtyUnit;
    private String isneedpic;

    //新协议价
    private BigDecimal futurePrice;
    //新协议生效时间
    private String futurePoPriceStartDate;

    private String devicesimpleno;

    private String supplierPhone;

}
