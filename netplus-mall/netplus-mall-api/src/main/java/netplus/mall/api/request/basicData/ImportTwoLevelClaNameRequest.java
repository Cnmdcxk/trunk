package netplus.mall.api.request.basicData;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ImportTwoLevelClaNameRequest implements Serializable {

    private String batchCode;
    private String fileUrl;
}
