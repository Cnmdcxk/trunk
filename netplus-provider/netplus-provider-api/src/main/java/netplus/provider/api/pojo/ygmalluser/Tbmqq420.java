package netplus.provider.api.pojo.ygmalluser;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ygmalluser.Tbmqq420")
public class Tbmqq420 {
    private String rolecode;

    private String rolename;

    private String roledesc;

    private String createuser;

    private String createdate;

    private String createtime;

    private String updateuser;

    private String updatedate;

    private String updatetime;

    private String rolepermission;
}