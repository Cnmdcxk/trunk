package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.api.pojo.ygmalluser.Tbmqq435;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class UpdateZkhPicRequest implements Serializable {

    private Tbmqq430 tbmqq430;
    private List<Tbmqq435> tbmqq435List;

}
