package netplus.utils.spec;

import lombok.Getter;
import netplus.utils.object.ObjectUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lhp on 2017/5/22.
 */

@Getter
public class SpecUtil {

    private String spec;
    private BigDecimal thick = BigDecimal.ZERO;
    private BigDecimal width = BigDecimal.ZERO;
    private BigDecimal lenght = BigDecimal.ZERO;
    private BigDecimal spec1 = BigDecimal.ZERO;
    private BigDecimal spec2 = BigDecimal.ZERO;
    private BigDecimal spec3 = BigDecimal.ZERO;
    private BigDecimal spec4 = BigDecimal.ZERO;
    private BigDecimal spec5 = BigDecimal.ZERO;
    private BigDecimal spec6 = BigDecimal.ZERO;
    private BigDecimal spec7 = BigDecimal.ZERO;
    private BigDecimal spec8 = BigDecimal.ZERO;
    private BigDecimal spec9 = BigDecimal.ZERO;
    private BigDecimal spec10 = BigDecimal.ZERO;

    public SpecUtil(String spec){
        this.spec = spec;
        this.parse();
    }

    private void parse() {

        List<String> specs = getField();

        R r = new R(specs.get(0));
        spec1 = r.a;
        spec2 = r.b;
        thick = spec2;

        R r2 = new R(specs.get(1));
        spec3 = r2.a;
        spec4 = r2.b;
        width = spec4;

        R r3 = new R(specs.get(2));
        spec5 = r3.a;
        spec6 = r3.b;
        lenght = spec6;

        R r4 = new R(specs.get(3));
        spec7 = r4.a;
        spec8 = r4.b;

        R r5 = new R(specs.get(4));
        spec9 = r5.a;
        spec10 = r5.b;

    }

    private List<String> getField() {
        List<String> list = new ArrayList<>();
        String[] strings = spec.split("[*|x]", 5);

        if (ObjectUtil.nonEmpty(strings)) {
            list.add(strings[0]);
            list.add(strings.length > 1 ? strings[1]:"");
            list.add(strings.length > 2 ? strings[2]:"");
            list.add(strings.length > 3 ? strings[3]:"");
            list.add(strings.length > 4 ? strings[4]:"");
        }else{
            list.add("");
            list.add("");
            list.add("");
            list.add("");
            list.add("");
        }
        return list;
    }

    private static class R{
        public BigDecimal a;
        public BigDecimal b;

        public R(String s) {
            this.a = BigDecimal.ZERO;
            this.b = BigDecimal.ZERO;
            
            if (ObjectUtil.nonEmpty(s)) {
            	String[] s1 = s.split("[-]");

                if (s1.length == 1) {
                    this.b = toD(s1[0]);
                }

                if (s1.length > 1) {
                    this.a = toD(s1[0]);
                    this.b = toD(s1[s1.length-1]);
                }
			}
        }

        private BigDecimal toD(String v) {
            try {
                return new BigDecimal(v);
            }catch (Exception e) {
                return BigDecimal.ZERO;
            }
        }
    }

}
