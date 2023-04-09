package netplus.mall.api.vo.good;

import lombok.Getter;
import lombok.Setter;
import netplus.utils.excel.entity.EntityWrap;
import netplus.utils.excel.entity.FieldWrap;
import netplus.utils.excel.validation.validations.CellName;
import netplus.utils.excel.validation.validations.MaxNumber;
import netplus.utils.excel.validation.validations.MinNumber;
import netplus.utils.excel.validation.validations.Required;

import java.io.Serializable;

@Setter
@Getter
public class ImportGoodInfoVo extends EntityWrap implements Serializable {


    @Required(message = "物料条码必填")
    @CellName(name = "物料条码（必填）")
    private FieldWrap matrltm;

    @CellName(name = "货号")
    private FieldWrap goodno;

    @CellName(name = "商品名")
    private FieldWrap productname;

    @CellName(name = "型规")
    private FieldWrap spec;

    @CellName(name = "计量单位")
    private FieldWrap qtyunit;

    @CellName(name = "最小起订量（非必填）")
    @MinNumber(message = "数量不能小于0", value = 0, equal = false)
    private FieldWrap minbuyqty;

    @Required(message = "含税价格必填")
    @CellName(name = "含税价格（必填）")
    @MinNumber(message = "数量不能小于0", value = 0, equal = false)
    @MaxNumber(message = "数量不能大于999999999", value = 999999999)
    private FieldWrap price;

    @CellName(name = "税率")
    private FieldWrap tax;
}
