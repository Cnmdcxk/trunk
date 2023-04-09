package netplus.provider.api.request;

import lombok.Data;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;

@Data
public class RolemanagerRequest extends RequestBase implements Serializable {

    private String roledesc;

    private String rolename;

    private String rolecode;

    private String searchKey;

}
