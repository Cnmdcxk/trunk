package netplus.mall.api.request.shoppingCart;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ImportShoppingCartRequest implements Serializable {
    private String batchCode;
    private String fileUrl;
    private String isContinue="N"; // Y 继续执行，N不继续执行

}
