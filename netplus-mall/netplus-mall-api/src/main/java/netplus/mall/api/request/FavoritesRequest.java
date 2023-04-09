package netplus.mall.api.request;

import lombok.Data;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Data
public class FavoritesRequest extends RequestBase implements Serializable {
    private String searchKey;
    private String categoryName;
    private String matrlno;
    private String isAdd;
}
