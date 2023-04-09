package netplus.component.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpApiResponse {

    private Integer status;
    private String message;
    private String data;
}
