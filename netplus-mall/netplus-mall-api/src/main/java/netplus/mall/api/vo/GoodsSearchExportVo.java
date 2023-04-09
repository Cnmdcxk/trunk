package netplus.mall.api.vo;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;

import java.io.Serializable;

@Getter
@Setter
public class GoodsSearchExportVo extends Tbmqq430 implements Serializable {

    /**
     * 商品品牌商品名
     */
    private String bandAndProductName;

    /**
     * 参考发货日期拼接后字符串
     */
    private String referenceDeliveryDateStr;

    /**
     * 创建时间yyyy-MM-dd HH:mm:ss
     */
    private String createTimeStr;

    /**
     * 修改时间yyyy-MM-dd HH:mm:ss
     */
    private String updateTimeStr;

    /**
     * 状态对应中文名
     */
    private String statusName;

    private String leadtimenumStr;

}
