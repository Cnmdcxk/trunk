package netplus.provider.api.vo;

import lombok.Getter;
import lombok.Setter;
import netplus.provider.api.pojo.ygmalluser.Tbmqq423;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class Tbmqq423Vo extends Tbmqq423 implements Serializable {

    private String roleisassignment;
    private String parentname;

    private List<Tbmqq423Vo> childprivilegelist;
}
