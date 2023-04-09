package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.api.pojo.ygmalluser.Tbmqq432;

import java.io.Serializable;

@Setter
@Getter
public class UpdateZkhGoodDetailRequest implements Serializable {

    private Tbmqq430 tbmqq430;
    private Tbmqq432 tbmqq432;

}
