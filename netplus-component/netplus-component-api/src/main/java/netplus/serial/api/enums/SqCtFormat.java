package netplus.serial.api.enums;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public enum SqCtFormat {

    DEFAULT("default",
            "默认模版",
            p -> p +  LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM")),
            i -> i.toString());

    public String code;
    public String name;
    public Function<String, String> getCodeFn;
    public Function<Integer, String> sqToString;

    SqCtFormat(String code, String name, Function<String, String> getCodeFn, Function<Integer, String> sqToString) {
        this.code = code;
        this.name = name;
        this.getCodeFn = getCodeFn;
        this.sqToString = sqToString;
    }

    public static SqCtFormat Of(String code) {
        if ("default".equals(code)) {
            return SqCtFormat.DEFAULT;
        }
        return SqCtFormat.DEFAULT;
    }

}
