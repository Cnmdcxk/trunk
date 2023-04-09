package netplus.provider.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class AssetsVo implements Serializable {

    // 待转固定资产编号
    private String assetsNo;

    // 待转固资产名称
    private String assetsName;


}
