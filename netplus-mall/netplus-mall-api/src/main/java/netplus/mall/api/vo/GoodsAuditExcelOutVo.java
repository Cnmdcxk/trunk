package netplus.mall.api.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsAuditExcelOutVo {

    @ExcelProperty("商品名称")
    private String productname;

    @ExcelProperty("品牌")
    private String brand;

    @ExcelProperty("货号")
    private String goodno;

    @ExcelProperty("型规")
    private String spec;

    @ExcelProperty("物料条码")
    private String matrltm;

    @ExcelProperty("物料编码")
    private String matrlno;

    @ExcelProperty("协议号")
    private String pono;

    @ExcelProperty("交货周期")
    private String leadtimenumStr;

    @ExcelProperty("物资采购人")
    private String agent;

    @ExcelProperty("协议价格")
    private BigDecimal  originprice;

    @ExcelProperty("商城价格")
    private BigDecimal price;

    @ExcelProperty("税率")
    private String taxStr;

    @ExcelProperty("计量单位")
    private String qtyunit;

    @ExcelProperty("商品组号")
    private String groupno;

    @ExcelProperty("合同有效期起")
    private String popricestartdateStr;

    @ExcelProperty("合同有效期止")
    private String popricedateStr;

    @ExcelProperty("创建时间")
    private String createTimeStr;

    @ExcelProperty("更新时间")
    private String  updateTimeStr;

    @ExcelProperty("供应商编码")
    private String supplierno;

    @ExcelProperty("供应商名称")
    private String suppliername;

    @ExcelProperty("状态")
    private String statusName;





}
