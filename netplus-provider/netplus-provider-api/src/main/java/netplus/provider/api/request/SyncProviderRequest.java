package netplus.provider.api.request;

import lombok.Getter;
import lombok.Setter;
import netplus.provider.api.pojo.ygmalluser.Tbdu01;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class SyncProviderRequest implements Serializable {

    private List<Tbdu01> tbdu01VoList;

}
