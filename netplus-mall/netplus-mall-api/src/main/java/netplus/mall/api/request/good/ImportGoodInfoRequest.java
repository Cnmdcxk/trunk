package netplus.mall.api.request.good;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ImportGoodInfoRequest implements Serializable {

    private String batchCode;
    private String fileUrl;

}
