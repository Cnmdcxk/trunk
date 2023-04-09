package netplus.joint.erp.api.response.out.JK0007;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class JK0007SubResponse implements Serializable {

    private String wzmcbm_pk;//物料id
    private String wztm;//物料条码
    private String wzbm;//物料编码
    private String wzmc;//物料名称
    private String ggxh;//物料规格型号
    private String jldw;//物料计量单位
    private String lbxh1;//类别序号1
    private String lbmc1;//类别名称1
    private String lbbm_pk1;//类别编码PK1
    private String lbxh2;//类别序号2
    private String lbmc2;//类别名称2
    private String lbbm_pk2;//类别编码PK2
    private String lbxh3;//类别序号3
    private String lbmc3;//类别名称3
    private String lbbm_pk3;//类别编码PK3
    private String lbxh4;//类别序号4
    private String lbmc4;//类别名称4
    private String lbbm_pk4;//类别编码PK4
    private String lbxh5;//类别序号5
    private String lbmc5;//类别名称5
    private String lbbm_pk5;//类别编码PK5
}
