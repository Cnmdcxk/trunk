package netplus.component.http;

import lombok.Getter;
import lombok.Setter;
import netplus.component.entity.data.KeyValue;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HttpApiRequest {

    private String url;
    private String apiName;
    private String content;
    private Boolean throwIfError;
    private List<KeyValue> headers = new ArrayList<>();


    public HttpApiRequest() {
        throwIfError = true;
    }

    public HttpApiRequest(String url, String apiName, String content) {
        this();

        this.url = url;
        this.apiName = apiName;
        this.content = content;
    }


}
