package netplus.provider.api.request;

import lombok.Data;
import netplus.component.entity.request.RequestBase;

import java.io.Serializable;
import java.util.List;
@Data
public class permissionsRequest extends RequestBase implements Serializable {
    private String code;

    private String name;

    private String privilegedesc;

    private String privilegetype;

    private String parentcode;

    private String sort;

    private String hidsort;

    private String isactive;

    private String belongto;

    private String isdefault;

    private String icon;

    private String pagevisible;

    private List<String> parentCodeList;

    private List<String> isActiveList;

    private List<String> isDefaultList;

    private String searchKey;


}
