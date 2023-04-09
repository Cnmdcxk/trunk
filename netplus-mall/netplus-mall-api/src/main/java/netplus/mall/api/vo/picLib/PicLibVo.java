package netplus.mall.api.vo.picLib;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class PicLibVo implements Serializable {

    private String createDate;
    private String updateDate;
    private List<Tbmqq429Vo> tbmqq429VoList;
}
