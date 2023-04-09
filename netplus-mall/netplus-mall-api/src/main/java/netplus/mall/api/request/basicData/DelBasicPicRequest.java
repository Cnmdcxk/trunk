package netplus.mall.api.request.basicData;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq407;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class DelBasicPicRequest implements Serializable {

    private List<Tbmqq407> tbmqq407KeyList;
}
