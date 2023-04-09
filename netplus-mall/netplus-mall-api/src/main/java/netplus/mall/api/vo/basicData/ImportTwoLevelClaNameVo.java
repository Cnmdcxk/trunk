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
public class ImportTwoLevelClaNameVo extends EntityWrap implements Serializable {

    @Required(message = "物料号必填")
    @CellName(name = "物料号（必填）")
    private FieldWrap matrlno;

    @Required(message = "商品名称")
    @CellName(name = "商品名称")
    private FieldWrap productName;

    @Required(message = "型规")
    @CellName(name = "型规")
    private FieldWrap spec;

    @Required(message = "计量单位")
    @CellName(name = "计量单位")
    private FieldWrap unit;

    @Required(message = "二级分类必填")
    @CellName(name = "二级分类（必填）")
    private FieldWrap twoLevelClaName;


}
