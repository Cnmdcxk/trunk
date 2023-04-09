package netplus.mall.api.request.advertising;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChangeAdvertisingStatusRequest {

    private List<String> ids;
}
