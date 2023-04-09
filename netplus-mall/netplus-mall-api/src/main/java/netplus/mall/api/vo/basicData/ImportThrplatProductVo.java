package netplus.mall.api.vo.basicData;

import lombok.Getter;
import lombok.Setter;
import netplus.utils.excel.entity.EntityWrap;
import netplus.utils.excel.entity.FieldWrap;
import netplus.utils.excel.validation.validations.CellName;
import netplus.utils.excel.validation.validations.Required;

import java.io.Serializable;

@Setter
@Getter
public class ImportThrplatProductVo extends EntityWrap implements Serializable {

    @Required(message = "物料条码（必填）")
    @CellName(name = "物料条码（必填）")
    private FieldWrap matrlTm;

    @Required(message = "供应商（必填）")
    @CellName(name = "供应商（必填）")
    private FieldWrap supplierName;

    @Required(message = "第三方平台货号（必填）")
    @CellName(name = "第三方平台货号（必填）")
    private FieldWrap goodNo;

    @CellName(name = "外部品名")
    private FieldWrap productName;

    @CellName(name = "外部型规")
    private FieldWrap spec;

    @CellName(name = "外部材质")
    private FieldWrap quality;

    @CellName(name = "外部计量单位")
    private FieldWrap unit;
}
