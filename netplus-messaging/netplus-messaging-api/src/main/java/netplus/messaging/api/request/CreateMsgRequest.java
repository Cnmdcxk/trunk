package netplus.messaging.api.request;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.enums.MsgId;

import java.io.Serializable;

@Setter
@Getter
public class CreateMsgRequest implements Serializable {
    private String userId; // 消息接受人
    private String createUser; // 消息创建人
    private String createUserName; //消息创建名称

    private MsgId msgId; // 消息类型
    private String[] param; // 消息内容参数

    private String url; //消息跳转地址 暂时没有用到
}
