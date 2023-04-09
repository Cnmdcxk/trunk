package netplus.mall.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ModifyBasicDataMaterialRequest implements Serializable {

    private String twolevelclaname;
    private String matrlno;
    private String spec;
    private String quality;
    private String productname;
    private String matrldesc;
    private String unit;
    private String isactive;
    private String thrplatgoodno;
    private String matrltm;
    private String suppliername;

}
