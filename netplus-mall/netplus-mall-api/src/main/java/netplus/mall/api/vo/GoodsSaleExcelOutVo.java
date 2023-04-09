package netplus.mall.api.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsSaleExcelOutVo {

    @ExcelProperty("商品名称")
    private String productname;


    @ExcelProperty("货号")
    private String goodno;

    @ExcelProperty("型规")
    private String spec;

    @ExcelProperty("物料条码")
    private String matrltm;

    @ExcelProperty("物料编码")
    private String matrlno;


    @ExcelProperty("交货周期")
    private String leadtimenumStr;


    @ExcelProperty("含税价格")
    private BigDecimal  price;

    @ExcelProperty("未税价格")
    private BigDecimal notprice;

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


    @ExcelProperty("状态")
    private String statusName;

}
