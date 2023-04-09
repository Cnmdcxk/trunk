package netplus.mall.api.enums;

/**
 * Created by py on 2021/12/9.
 */
public enum  AddrTypeEnum {

    ZK("1","总库"),
    KDD("2","快递点");


    private String code;
    private String name;

    AddrTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
