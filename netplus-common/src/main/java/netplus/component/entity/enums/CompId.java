package netplus.component.entity.enums;


public enum CompId {

    RZ("rizhao");

    private String value;

    CompId(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
