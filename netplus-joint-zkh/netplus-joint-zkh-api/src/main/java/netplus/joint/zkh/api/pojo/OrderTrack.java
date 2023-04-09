package netplus.joint.zkh.api.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderTrack {
    private String msgTime;// String Y 时间
    private String content;// String Y 配送信息内容
    private String operator;// String N 操作员
}
