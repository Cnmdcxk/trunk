package netplus.component.entity.enums;

public enum  OneOrZeroEnum {

    Zero("0", 0),
    One("1", 1);

    private String value;
    private int value2;

    OneOrZeroEnum(String value, int value2) {

        this.value = value;
        this.value2 = value2;
    }

    public String getValue() {
        return this.value;
    }

    public int getValue2() {
        return this.value2;
    }
}
