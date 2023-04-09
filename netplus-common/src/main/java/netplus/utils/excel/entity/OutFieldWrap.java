package netplus.utils.excel.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by lhp on 2017/6/27.
 * excel 导出辅助类
 */
public class OutFieldWrap implements Serializable {

    @Getter
    @Setter
    @ToString
    public static class FieldInfo {
        private String name;
        private String cellName;
        private int index;
        private Field field;
    }
}
