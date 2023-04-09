package netplus.messaging.api.request;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.enums.MsgTypeEnum;

@Getter
@Setter
public class SendMsgRequest {

    private String sendUserNo;

    private String receiveUserNo;

    private MsgTypeEnum msgType;

    private String[] params;

    private String url;
}
