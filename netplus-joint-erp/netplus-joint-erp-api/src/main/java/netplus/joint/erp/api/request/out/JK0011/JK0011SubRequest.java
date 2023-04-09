package netplus.joint.erp.api.request.out.JK0011;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class JK0011SubRequest {
    private String scddmxbm_pk;
    private String wzmcbm_pk;
    private String wztm;
    private String wzbm;
    private String wzmc;
    private String ggxh;
    private String jldw;
    private String wzcgr;
    private BigDecimal jhsl;
    private BigDecimal hsdj;
    private BigDecimal sl;
    private String xmmc;//		String	500	是		项目名称（按指定格式传入如：项目编号xmbh：B2116，项目名称xmmc：炼钢三分厂新增9#连铸机及配套设施项目B2116）
    private String xmbh;//		String	50	是		项目编号
    private String xzsbsqdpk;//		String	50	是		新增设备申请单主键
    private String xzsbsqdbh;//		String	500	是		新增设备申请单编号
    private String dhrq;//		String	50	是		下单要求到货日期 YYYY-MM-DD  如：2021-07-01
    private String sfsystz;//		String	1	是		是否使用系统图纸  0不使用  1使用
    private String jhlybj;//		String	1	是		计划来源标记 0业务系统计划  1商城计划   2智能柜计划 （写死1）
    private String ddshdd;//		String	200	是		下单选择的送货地点，存入计划明细，交互平台预约的地址默认取该地址，供应商可以修改，详细地址
    private String sfzkbj;//		String	1	是		是否送总库标记，存入计划明细（快递柜的计划人报验后自动入库出库，若后续确认进二级库时则报验时提供选择是否入二级库）
    private String bmtxbm_pk;//		String	50	是		条线主键
    private String bmtxmc;//		String	100	是		条线名称
    private String shrmc; //收货人名称
    private String shrgh; //收货人工号
    private String shrlxfs; //收货人联系方式
    private String bz;//普通备注
    private String gjbz;//高价备注
    private String gzsbjh;//工装设备简号
}
