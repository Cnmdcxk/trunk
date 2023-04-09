package netplus.mall.api.request.basicData;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq407;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class InitBasicPicRequest implements Serializable {

    private String supplierNo;
    private String scope;

    List<Tbmqq407> tbmqq407List;
}
