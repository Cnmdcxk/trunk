package netplus.joint.erp.api.enums;

//import netplus.component.entity.exceptions.NetPlusException;

public enum  ErpOrderStatusEnum {


    O("O", "", "", ""),
    P("P", "审核中", "DSH", "待审核"),
    Q("Q", "审核驳回", "CGBH", "审核驳回"),
    S("S", "采购评审中", "CCPSZ", "采购评审中"),
    C("C", "采购评审驳回", "CCPSZ", "采购评审驳回"),
    N("N", "已订购", "YDG", "已订购"),
    R("R", "交货中", "JHZ", "交货中"),
    X("X", "已结案", "YJA", "已结案");



    private String code;
    private String name;
    private String ptCode;
    private String ptName;

    ErpOrderStatusEnum (String code, String name, String ptCode, String ptName) {
        this.code = code;
        this.name = name;
        this.ptCode = ptCode;
        this.ptName = ptName;
    }

    public String getCode () {
        return code;
    }

    public String getName () {
        return name;
    }

    public String getPtCode () {
        return ptCode;
    }

    public String getPtName () {
        return ptName;
    }

    public static String getPtCode (String code) {

        for (ErpOrderStatusEnum e: ErpOrderStatusEnum.values()) {
            if (e.getCode().equals(code)) {
                return e.getPtCode();
            }
        }

        return "";
    }



}
