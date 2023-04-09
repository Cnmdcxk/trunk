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
public class ImportErpClaVo extends EntityWrap implements Serializable {

    @Required(message = "ERP物料分类必填")
    @CellName(name = "ERP物料分类（必填）")
    private FieldWrap erpClaName;

    @Required(message = "二级分类必填")
    @CellName(name = "二级分类（必填）")
    private FieldWrap twoLevelClaName;


    @Required(message = "一级分类必填")
    @CellName(name = "一级分类（必填）")
    private FieldWrap oneLevelClaName;

    @Required(message = "大类必填")
    @CellName(name = "大类（必填）")
    private FieldWrap categoryName;
}
