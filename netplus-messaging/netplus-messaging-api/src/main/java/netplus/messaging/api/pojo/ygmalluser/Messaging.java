package netplus.messaging.api.pojo.ygmalluser;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ygmalluser.Messaging")
public class Messaging {
    private String id;

    private String senduserno;

    private String receiveuserno;

    private Integer messagetype;

    private String messagegroup;

    private Integer isread;

    private String messagecontent;

    private String receivetime;

    private String url;

}