package netplus.mall.api.vo.shoppingCart;

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
public class ImportShoppingCartVo extends EntityWrap implements Serializable {

    @Required(message = "物料条码必填")
    @CellName(name = "物料条码（必填）")
    private FieldWrap matrlTm;

    @Required(message = "数量必填")
    @CellName(name = "数量（必填，大于最小起订量或者大于0）")
    @MinNumber(message = "数量不能小于0", value = 0, equal = false)
    @MaxNumber(message = "数量不能大于999999999", value = 999999999)
    private FieldWrap qty;


}
