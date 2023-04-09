package netplus.component.entity.iface;

public enum SysCodeEnum {

    ERP("ERP", "ERP系统"),
    PT("PT", "采购平台"),
    OB("OB", "欧贝系统"),
    ZKH("ZKH", "震坤行系统"),
    SPL("SPL", "审批流系统"),
    JOB("JOB", "定时任务系统"),
    CK("CK", "仓库系统"),
    OA("OA", "OA系统");

    public String code;
    public String name;

    SysCodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
