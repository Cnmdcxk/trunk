package netplus.mall.api.vo.picLib;

import lombok.Getter;
import lombok.Setter;
import netplus.mall.api.pojo.ygmalluser.Tbmqq429;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class DelPicLibRequest implements Serializable {

    private List<Tbmqq429> tbmqq429List;
}
