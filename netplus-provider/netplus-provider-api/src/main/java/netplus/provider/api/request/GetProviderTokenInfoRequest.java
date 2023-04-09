package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GetProviderTokenInfoRequest implements Serializable {
    private String ticket;
    private String sourceFrom;
}
