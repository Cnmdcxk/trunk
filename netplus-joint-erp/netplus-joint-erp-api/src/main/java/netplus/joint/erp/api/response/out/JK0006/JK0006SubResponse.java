package netplus.joint.erp.api.response.out.JK0006;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0006SubResponse implements Serializable {

    private String xzsbsqdpk;//新增设备申请单主键
    private String xzsbsqdbh;//新增设备申请单编号
    private String sbmc;//设备名称
    private String ggxh;//规格型号
    private String sbdwmc;//申报单位
    private String sydwmc;//使用单位
    private String zbxzcbz;//资本性标志（0：资产、1：费用）
    private String shzt;//OA审批状态（2：已审批，其他均显示审核中）
    private String sfxz;//是否已使用（0：未使用、1：已使用），备注已使用的单号需设定为不允许选择，
    //该是否已使用只判断仓库系统中是否已使用，商城中的需要自行判断
}
