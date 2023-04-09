package netplus.provider.api.vo;

import lombok.Getter;
import lombok.Setter;
import netplus.provider.api.pojo.ygmalluser.Tbdu01;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sy on 2021/1/7.
 */
@Getter
@Setter
public class Tbdu01Vo extends Tbdu01 implements Serializable {

    private String deptName;
    private String postName;
    private String roleName;
    private String roleCode;
    private String createTime;
    private List<Tbmqq422Vo> tbmqq422VoList;

}
