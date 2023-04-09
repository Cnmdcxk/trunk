package netplus.mall.api.request.advertising;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AddAdvertisingRequest  implements Serializable {

    private String imagePath;

    private String imageName;
}
