package netplus.mall.api.request.HarvesterManagement;

import lombok.Data;
import netplus.component.entity.data.MinMax;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Data
public class CommodityHarvestRequest extends RequestBase implements Serializable {
    private String searchKey; // 搜寻键
    private String code;// 编码
    private String province;// 省份
    private String city;// 市
    private String consigneeaddr;// 地址
    private String addrtype; // 类型
    private MinMax createDate; // 时间范围
}
