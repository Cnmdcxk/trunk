package netplus.provider.api.enums;

import java.util.ArrayList;
import java.util.List;

public enum LogTypeEnums {

    LOGIN("1", "登录"),
    NORMAL("2", "正常"),
    ERROR("3", "错误"),
    LOGOUT("4", "退出");

    private String code;
    private String name;

    LogTypeEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode () {
        return code;
    }

    public String getName () {
        return name;
    }


    public static String getName (String code) {

        for (LogTypeEnums e: LogTypeEnums.values()) {
            if (e.getCode().equals(code)) {
                return e.getName();
            }
        }

        return "";
    }


    public static String getCode (String name) {

        for (LogTypeEnums e: LogTypeEnums.values()) {
            if (e.getName().equals(name)) {
                return e.getCode();
            }
        }

        return "";
    }

    public static List<String> getContainsNameCode (String name) {
        List<String> list=new ArrayList<>();
        for (LogTypeEnums e: LogTypeEnums.values()) {
            if (e.getName().contains(name)) {
                list.add(e.getCode());
            }
        }
        if(list.isEmpty()){
            list.add("-1");
        }
        return list;
    }

}
