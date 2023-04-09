package netplus.serial.api.enums;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public enum SqFormat {

    DEFAULT("default",
            "默认模版",
            p -> p +  LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
            i -> i.toString().length() > 4 ? i.toString() : String.format("%04d", i)),



    EASY("easy",
         "模版格式:前缀+7位流水号，P0000001",
         p -> p,
         i -> i.toString().length() > 7 ? i.toString() : String.format("%07d", i));




    public String code;
    public String name;
    public Function<String, String> getCodeFn;
    public Function<Integer, String> sqToString;

    SqFormat(String code, String name, Function<String, String> getCodeFn, Function<Integer, String> sqToString) {
        this.code = code;
        this.name = name;
        this.getCodeFn = getCodeFn;
        this.sqToString = sqToString;
    }

    public static SqFormat Of(String code) {

        if ("default".equals(code)) {
            return SqFormat.DEFAULT;
        }

        if ("easy".equals(code)) {
            return SqFormat.EASY;
        }

        return SqFormat.DEFAULT;
    }

}
