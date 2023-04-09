package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.api.vo.Tbmqq435Vo;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class BatchInsertGoodRequest implements Serializable {
    private List<Tbmqq430> tbmqq430List;
    private List<Tbmqq435Vo> tbmqq435List;

}
