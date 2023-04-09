package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class GetProjectNoRequest implements Serializable {

    private String searchKey;
    private List<String> projectNoList;
    private List<String> gsNoList;
    private int pageSize;
    private int pageIndex;
}
