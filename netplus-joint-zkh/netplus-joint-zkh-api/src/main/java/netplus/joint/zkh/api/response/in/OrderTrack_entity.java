package netplus.joint.zkh.api.response.in;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderTrack_entity implements Serializable {
    //时间
    private String msgTime;

    //配送信息内容
    private String content;

    //操作员
    private String operator;
}
