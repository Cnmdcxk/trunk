package netplus.mall.api.request.picLib;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetPicLibListRequest implements Serializable {

    private String searchKey;
    private String pictureType;
    private String deleted;
}
