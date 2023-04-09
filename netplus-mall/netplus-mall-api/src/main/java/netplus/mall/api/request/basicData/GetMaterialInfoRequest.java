package netplus.mall.api.request.basicData;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class GetMaterialInfoRequest implements Serializable {

    private String matrlNo;
    private List<String> matrlNoList;
}
