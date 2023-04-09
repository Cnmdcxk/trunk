package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetAssetsRequest implements Serializable {

    // 工程项目编号
    private String projno;
}
