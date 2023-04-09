package netplus.joint.zkh.api.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GoodsDetailVo {

    //商品编号
    private String sku;

    //重量
    private String weight;

    //主图地址 返回主图绝对地址路径
    private String fullImagePath;

    // 品牌
    private String brandName;

    // 商品名称
    private String name;

    //  产地
    private String productArea;

    // 销售单位
    private String saleUnit;

    //类⽬
    private List<String> category;

    // 最⼩起订量
    private int moq;

    // 物料号
    private String mfgSku;

    // 制造商型号（制造商物料号）
    private String manufacturerMaterialNo;

    //预计发货⽇单位：天 如果有现货则传0。商品参考发货⽇，精确发货⽇参考库存接⼝
    private int deliveryTime;

    //是否允许退换货货0：允许退换货 1：不允许退换货默认为0，不传默认按0处理
    private int isReturn;

    //String Y 商品描述 HTML⻚⾯，可直接展现
    private String introduction;

    //包装信息 包装描述信息
    private String wareQD;

    // param_entity[] N 规格属性信息
    private List<Attribute> param;

    // String N 是否是油类 0否1是
    private String isOil;

    // String N 成品油销售单位 当isOil为1时，此字段显示
    private String settleUnit;

    // String N 分⼦ 当isOil为1时，此字段显示
    private String molecule;

    // String N 分⺟ 当isOil为1时，此字段显示
    private String denominator;

    // String N 油类转换率 当isOil为1时，此字段显示*/
    private String settleRate;

    // String N 商品税收分类编码
    private String taxCode;

    // String N 产品组 震坤⾏产线标识，样例：MRO
    private String group;

    // String N 搜索关键词多个关键词⽤中英⽂标点符号分隔，如 “照明箱、8回路 ，PZ30”
    private String keywords;
}
