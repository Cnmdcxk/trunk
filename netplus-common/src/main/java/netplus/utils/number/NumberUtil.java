package netplus.utils.number;

import netplus.utils.object.ObjectUtil;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 精度计算
 */
public class NumberUtil {

    public static Integer SCALE_0 = 0;
    public static Integer SCALE_2 = 2;
    public static Integer SCALE_3 = 3;
    public static Integer SCALE_4 = 4;
    public static BigDecimal ZORE = BigDecimal.ZERO;
    public static BigDecimal ONE = new BigDecimal("1");
    public static BigDecimal HUNDRED = new BigDecimal("100");


    public static boolean isIntBig (BigDecimal v) {
        BigDecimal d = v.divideAndRemainder(NumberUtil.ONE)[1];
        return  NumberUtil.ZORE.equals(d);
    }

    public static boolean isTimeBig (BigDecimal v, BigDecimal v2) {
        BigDecimal d = v.divideAndRemainder(v2)[1];
        return isIntBig(d);
    }

    //字符串是否是数字
    public static boolean isNumBig (String v) {
        try {
            BigDecimal d = new BigDecimal(v);
            return true;

        }catch (Exception e) {
            return false;
        }
    }


    //整数税率转小数
    public static BigDecimal getTax(BigDecimal taxRate) {
        return div(taxRate, HUNDRED, SCALE_4);
    }

    // 不含税单价
    public static BigDecimal noTaxPrice(BigDecimal v, BigDecimal taxRate) {
        return formatPrice(div(v, BigDecimal.ONE.add(taxRate), SCALE_4));
    }

    //含税单价
    public static BigDecimal taxPrice (BigDecimal v, BigDecimal tax) {
        return formatPrice(mul(v, BigDecimal.ONE.add(tax), SCALE_4));
    }

    //小数保留p位小数，向上取整
    public static Double roundCeil(Double v, Integer p) {
        if (ObjectUtil.isEmpty(v))
            return 0.00;

        BigDecimal bg = new BigDecimal(Double.toString(v));
        return bg.setScale(p, BigDecimal.ROUND_DOWN).doubleValue();
    }

    //小数保留p位小数，向上取整
    public static BigDecimal roundCeil(BigDecimal v, Integer p) {
        if (v == null)
            return ZORE;

        return v.setScale(p, BigDecimal.ROUND_DOWN);
    }
    
    /**
     * 字符转BigDecimal
     * @param v
     * @param scale
     * @return
     */
    public static BigDecimal s2bS(String v, Integer scale){
    	return BigDecimal.valueOf(Double.valueOf(v)).setScale(scale, BigDecimal.ROUND_DOWN);
    }
    
    /**
     * 字符转重量BigDecimal
     * @param v
     * @return
     */
    public static BigDecimal s2bWeight(String v){
    	return s2bS(v, SCALE_4);
    }
    
    /**
     * 字符转单价BigDecimal
     * @param v
     * @return
     */
    public static BigDecimal s2bPrice(String v){
    	return s2bS(v, SCALE_4);
    }

    /**
     * 字符转挂牌单价(小数点后6位)BigDecimal
     * @param v
     * @return
     */
    public static BigDecimal s2bListPrice(String v){
        return s2bS(v, SCALE_4);
    }
    
    /**
     * 字符转金额BigDecimal
     * @param v
     * @return
     */
    public static BigDecimal s2bAmt(String v){
    	return s2bS(v, SCALE_4);
    }
    
    /**
     * 单价double转BigDecimal
     * @param v
     * @return
     */
    public static BigDecimal d2bPrice(Double v){
    	return BigDecimal.valueOf(v).setScale(SCALE_4, BigDecimal.ROUND_DOWN);
    }
    
    /**
     * 金额double转BigDecimal
     * @param v
     * @return
     */
    public static BigDecimal d2bAmt(Double v){
    	return BigDecimal.valueOf(v).setScale(SCALE_4, BigDecimal.ROUND_DOWN);
    }

    
    /**
     * 重量double转BigDecimal
     * @param v
     * @return
     */
    public static BigDecimal d2bWeight(Double v){
    	return BigDecimal.valueOf(v).setScale(SCALE_4, BigDecimal.ROUND_DOWN);
    }
    
    
    /**
     * integer转BigDecimal
     * @param v
     * @return
     */

    public static BigDecimal i2b (Integer v) {
        return BigDecimal.valueOf(v).setScale(SCALE_0, BigDecimal.ROUND_DOWN);
    }

    /**
     * 厚度double转BigDecimal
     * @param v
     * @return
     */
    public static BigDecimal d2bThick(Double v){
        return BigDecimal.valueOf(v).setScale(SCALE_4, BigDecimal.ROUND_DOWN);
    }

    /**
     * 长度double转BigDecimal
     * @param v
     * @return
     */
    public static BigDecimal d2bLength(Double v){
        return BigDecimal.valueOf(v).setScale(SCALE_4, BigDecimal.ROUND_DOWN);
    }

    /**
     * 宽度double转BigDecimal
     * @param v
     * @return
     */
    public static BigDecimal d2bWidth(Double v){
        return BigDecimal.valueOf(v).setScale(SCALE_4, BigDecimal.ROUND_DOWN);
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @param scale 保留精度
     * @return 两个参数的和
     */
    public static BigDecimal add(BigDecimal v1, BigDecimal v2, int scale){
    	if (ObjectUtil.isEmpty(scale)) {
			scale = SCALE_4;
		}
    	
    	if(v1 == null) {
    		return v2.setScale(scale, BigDecimal.ROUND_DOWN);
    	}
    	
    	if(v2 == null) {
    		return v1.setScale(scale, BigDecimal.ROUND_DOWN);
    	}
    	
        return v1.add(v2).setScale(scale, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal add(BigDecimal a, BigDecimal... d) {
        BigDecimal r = a;
        if (d.length > 0) {
            for (int i = 0; i < d.length; i++) {
                if (d[i] != null) {
                    r = r.add(d[i]);
                }
            }
        }
        return r;
    }

    /**
     * 提供精确的加法运算。
     * @param v 被加数
     * @return 两个参数的和
     */
    public static BigDecimal accumulation(int scale, BigDecimal... v){
        if (v == null)
            return BigDecimal.ZERO;

        if (v.length == 0)
            return BigDecimal.ZERO;

        BigDecimal result = v[0];
        for (int i = 1; i < v.length; i++) {
            result = add(result, v[i], scale);
        }
        return result;
    }


    /**
     * 提供精确的加法运算。
     * 金额格式化
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add2Amt(BigDecimal v1, BigDecimal v2){
    	return add(v1, v2, SCALE_4);
    }

    /**
     * 提供精确的加法运算。
     * 金额格式化
     * @param v1 参数
     * @return 参数的和
     */
    public static BigDecimal add2Amt(BigDecimal... v1){
        return accumulation(SCALE_4, v1);
    }
    
    /**
     * 提供精确的加法运算。
     * 单价格式化(后来需求变更保留8位)
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add2Price(BigDecimal v1, BigDecimal v2){
    	return add(v1, v2, SCALE_4);
    }
    /**
     * 提供精确的加法运算。
     * 重量格式化
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add2Weight(BigDecimal v1, BigDecimal v2){
    	return add(v1, v2, SCALE_4);
    }

    /**
     * 提供精确的加法运算。
     * 重量格式化
     * @param v1 被加数
     * @return 两个参数的和
     */
    public static BigDecimal add2Weight(BigDecimal... v1){
        return accumulation(SCALE_4, v1);
    }
    
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @param scale 保留精度
     * @return 两个参数的差
     */
    public static BigDecimal sub(BigDecimal v1, BigDecimal v2, int scale){
    	if (ObjectUtil.isEmpty(scale)) {
			scale = SCALE_3;
		}
    	
    	if (null == v1) {
			return ZORE.subtract(v2).setScale(scale, BigDecimal.ROUND_DOWN);
		}
    	
    	if (null == v2) {
			return v1.setScale(scale, BigDecimal.ROUND_DOWN);
		}
    	
    	return v1.subtract(v2).setScale(scale, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal sub(BigDecimal v1,BigDecimal v2){
        return v1.subtract(v2);
    }
    
    /**
     * 减法 金额格式化 (v1-v2)
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub2Amt(BigDecimal v1, BigDecimal v2){
    	return sub(v1, v2, SCALE_4);
    }
    
    /**
     * 减法 价格格式化(v1-v2)
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub2Price(BigDecimal v1, BigDecimal v2){
    	return sub(v1, v2, SCALE_4);
    }
    
    /**
     * 减法 重量格式化(v1-v2)
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub2Weight(BigDecimal v1, BigDecimal v2){
    	return sub(v1, v2, SCALE_4);
    }
    
    /**
     * 提供精确的乘法运算。
     * @param v1 被乘数
     * @param v2 乘数
     * @param scale 保留精度
     * @return 两个参数的积
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2, int scale){
    	if (ObjectUtil.isEmpty(scale)) {
			scale = SCALE_3;
		}
    	
    	if (null == v1) {
			return ZORE;
		}
    	
    	if (null == v2) {
			return ZORE;
		}
    	
    	return v1.multiply(v2).setScale(scale, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal mul(BigDecimal a, BigDecimal... d) {
        BigDecimal r = a;
        if (d.length > 0) {
            for (int i = 0; i < d.length; i++) {
                if (d[i] != null) {
                    r = r.multiply(d[i]);
                }
            }
        }
        return r;
    }
    
    /**
     * 乘法，金额格式化
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul2Amt(BigDecimal v1, BigDecimal v2){
        return mul(v1, v2, SCALE_4);
    }
    
    /**
     * 乘法，价格格式化
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul2Price(BigDecimal v1, BigDecimal v2){
        return mul(v1, v2, SCALE_4);
    }
    

    /**
     * 乘法，重量格式化
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal mul2Weight(BigDecimal v1, BigDecimal v2){
        return mul(v1, v2, SCALE_4);
    }
    
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale){

    	if (ObjectUtil.isEmpty(scale)) {
			scale = SCALE_4;
		}
    	
    	if (null == v1) {
			return ZORE;
		}
    	
    	if (null == v2) {
			return ZORE;
		}

    	return v1.setScale(scale + 2).divide(v2, BigDecimal.ROUND_DOWN).setScale(scale, BigDecimal.ROUND_DOWN);
    }
    
    /**
     * 除法，金额格式化
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal div2Amt(BigDecimal v1, BigDecimal v2){
        return div(v1, v2, SCALE_4);
    }
    
    /**
     * 除法，价格格式化
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal div2Price(BigDecimal v1, BigDecimal v2){
        return div(v1, v2, SCALE_4);
    }
    
    /**
     * 除法，重量格式化
     * @param v1
     * @param v2
     * @return
     */
    public static BigDecimal div2Weight(BigDecimal v1, BigDecimal v2){
        return div(v1, v2, SCALE_4);
    }

    /**
     * 比较-大于
     * @param v1
     * @param v2
     * @return
     */
    public static boolean gt(BigDecimal v1, BigDecimal v2) {
        return v1.compareTo(v2) > 0;
    }

    /**
     * 比较-大于等于
     * @param v1
     * @param v2
     * @return
     */
    public static boolean gte(BigDecimal v1, BigDecimal v2) {
        return v1.compareTo(v2) >= 0;
    }

    /**
     * 比较-小于
     * @param v1
     * @param v2
     * @return
     */
    public static boolean lt(BigDecimal v1, BigDecimal v2) {
        return v1.compareTo(v2) < 0;
    }

    /**
     * 比较-小于等于
     * @param v1
     * @param v2
     * @return
     */
    public static boolean lte(BigDecimal v1, BigDecimal v2) {
    	return v1.compareTo(v2) <= 0;
    }

    /**
     * 比较-是否相等
     * @param v1
     * @param v2
     * @return
     */
    public static boolean equals(BigDecimal v1, BigDecimal v2) {
        return v1.compareTo(v2)==0;
    }

    /**
     * 重量格式化
     */
    public static BigDecimal formatWeight(BigDecimal v) {
        return v.setScale(SCALE_4, BigDecimal.ROUND_DOWN);
    }
    
    /**
     * 价格格式化
     * @param v
     * @return
     */
    public static BigDecimal formatPrice(BigDecimal v) {
    	return v.setScale(SCALE_4, BigDecimal.ROUND_DOWN);
	}
    
    /**
     * 金额格式化
     * @param v
     * @return
     */
    public static BigDecimal formatAmt(BigDecimal v) {
    	return v.setScale(SCALE_4, BigDecimal.ROUND_DOWN);
	}

    /**
     * 展示重量保留3位
     * @param v
     * @return
     */
    public static BigDecimal showWeight(BigDecimal v) {
        return v.setScale(SCALE_4, BigDecimal.ROUND_DOWN);
    }

    /**
     * 计算百分比
     */
    public static BigDecimal calculatePrecent(BigDecimal num,int scale,BigDecimal ... nums){

        if(ObjectUtil.isEmpty(nums)){
            return null;
        }
        if(ObjectUtil.isEmpty(num)){
            return new BigDecimal(0).setScale(scale);
        }

        BigDecimal tatal = Arrays.stream(nums).reduce(new BigDecimal(0),(sum,item)->{
            return add(sum,item,scale);
        });

        if(BigDecimal.ZERO.compareTo(tatal)==0){
            return BigDecimal.ZERO;
        }
        return div(num,tatal,scale);

    }

    public static BigDecimal dev(BigDecimal a, BigDecimal... d) {
        BigDecimal r = a;
        if (d.length > 0) {
            for (int i = 0; i < d.length; i++) {
                if (d[i] == null || d[i].compareTo(BigDecimal.ZERO) == 0) {
                    r = BigDecimal.ZERO;
                }else{
                    r = r.divide(d[i], 6, BigDecimal.ROUND_DOWN);
                }

            }
        }
        return r;
    }

    public static int ceil(BigDecimal a) {
        return a.setScale(0, BigDecimal.ROUND_DOWN).intValue();
    }

    public static Double round(Double v, Integer p) {
        if (ObjectUtil.isEmpty(v))
            return 0.0;

        BigDecimal bg = new BigDecimal(v);
        return bg.setScale(p, BigDecimal.ROUND_DOWN).doubleValue();
    }

    public static BigDecimal round(BigDecimal v, Integer p) {
        if (ObjectUtil.isEmpty(v)) {
            return BigDecimal.ZERO;
        }
        return v.setScale(p, BigDecimal.ROUND_DOWN);
    }

    //取余
    public static BigDecimal surplus(BigDecimal v, BigDecimal v2) {
        BigDecimal[] results = v.divideAndRemainder(v2);
        return roundCeil(results[1], SCALE_4);

    }

}
