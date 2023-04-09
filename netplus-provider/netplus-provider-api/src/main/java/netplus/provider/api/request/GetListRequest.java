package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetListRequest {
    private String compid;
    private String codetype;
    private String codeno;

}
