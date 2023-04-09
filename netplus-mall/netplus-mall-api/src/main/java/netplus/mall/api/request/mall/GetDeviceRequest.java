package netplus.mall.api.request.mall;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetDeviceRequest implements Serializable {
    private String searchKey;
}
