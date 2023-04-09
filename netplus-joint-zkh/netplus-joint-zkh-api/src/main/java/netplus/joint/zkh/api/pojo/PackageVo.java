package netplus.joint.zkh.api.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PackageVo {
    private String packageId;                 // String Y 发货单号
    private List<OrderTrack> orderTrack;      // orderTrack_entity[] N 配送信息
    private boolean selfOperated;             // Boolean Y 是否⾃营 true: ⾃营物流轨迹 false: ⾮⾃营物流轨迹
    private SelfLogisticsInfo selfLogisticsInfo;         // SelfLogisticsInfo N ⻋辆轨迹 信息是⾃营物流轨迹返回⻋辆轨迹 信息
    private String type;
}
