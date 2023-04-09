package netplus.joint.zkh.api.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsPriceVo {
   private String skuId; // String Y
   private Double price; // Double Y 含税协议价 保留2位⼩数
   private Double nakedPrice; // Double Y 未税协议价 保留2位⼩数（如果客户以未税⽅式对接，该字段必填）
   private Double ecPrice; // Double Y 含税官⽹价(震坤⾏市场价) 保留2位⼩数
   private Double ecNakedPrice; // Double Y 未税官⽹价(震坤⾏）保留2位⼩数（如果客户以未税⽅式对接，该字段必填）
   private String taxRate; // String Y 税率
}
