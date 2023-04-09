package netplus.component.entity.enums;


public enum YesNoEnum {
    Yes("Y"),
    No("N");
    private String value;
    YesNoEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}
