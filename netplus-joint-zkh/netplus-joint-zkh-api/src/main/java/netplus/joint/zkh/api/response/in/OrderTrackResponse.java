package netplus.joint.zkh.api.response.in;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderTrackResponse implements Serializable {

    //发货单号
    private String packageId;

    //配送信息
    private List<OrderTrack_entity> orderTrack;

    //是否⾃营
    private boolean selfOperated;

    //⻋辆轨迹信息
    private SelfLogisticsInfo selfLogisticsInfo;

}
