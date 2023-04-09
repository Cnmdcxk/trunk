package netplus.excel.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Data
public class Field implements Serializable {
    private String val;
    private String name;
    private String code;

    private int intval;
    private BigDecimal BigDecimalval;
    private String nval; //新值
    private boolean valid;
    private String mgs;
    private Date dateval;

    public Field(String value) {
        val = value;
        valid = true;
        mgs = "";
        intval = 0;
        BigDecimalval = new BigDecimal("0");
    }

    public Field(String value, String name, String code) {
        this.code = code;
        this.val = value;
        this.nval = value;
        this.name = name;
    }

    public Field(String value, String name, String code, Integer intval) {
        this.code = code;
        this.val = value;
        this.nval = value;
        this.name = name;
        this.intval = intval;
    }

    public Field(BigDecimal BigDecimalval) {
        this.BigDecimalval = BigDecimalval;
        this.val = BigDecimalval.toString();
        this.nval = this.val;
    }

    public Field(Integer intval) {
        this.intval = intval;
        this.val = intval.toString();
        this.nval = this.val;
    }

    public String toString(){
        return "name:" + name + ",code:"+code + ",nval:" + nval + ",val:" + val;
    }

    public int toInt() {
        return intval;
    }

    public BigDecimal toBigDecimal() {
        return BigDecimalval;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("valid", valid);
        map.put("msg", mgs);
        map.put("value", val);

        return map;
    }

    public Date toDate(){
        return dateval;
    }

    public boolean isValid() {
        return valid;
    }
}
