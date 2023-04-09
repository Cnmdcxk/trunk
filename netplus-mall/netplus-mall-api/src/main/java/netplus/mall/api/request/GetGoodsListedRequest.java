package netplus.mall.api.request;

import lombok.Data;
import netplus.component.entity.data.MinMax;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;
import java.util.List;

@Data
public class GetGoodsListedRequest extends RequestBase implements Serializable {
    private String searchKey; // 搜索贱
    private MinMax createDate;// 创建时间(年月日)
    private MinMax updateDate;// 修改时间(年月日)
    private String categoryName; // 分类名称
    private String categoryPk; // 分类pk
    private String categoryNameList; // 分类名称集合
    private String twolevelclaName; // 二级分类名称
    private String onelevelclaName; // 一级分类名称
    private String brand; // 品牌
    private String suppliername; // 供应商名称
    private String status; // 状态
    private String hasPic; // 图片
    private MinMax price; // 价格
    private String groupNo; // 分组编号
    private String tapStatus;
    private String matrltm;
    private List<String> dsStatusList; //电商组审核状态
    private List<String> cgStatusList; //采购员审核状态
    private List<String> statusList;
    private String taxException;
    private String isUpPicture;
    private List<String> categoryList;
    private List<String> supplierNoList;
    private List<String> brandList;
    private List<String> agentList;
}
