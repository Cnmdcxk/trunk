package netplus.utils.object;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ObjectUtil {

    protected static Log logger = LogFactory.getLog(ObjectUtil.class);


    public static Boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }

        return false;
    }

    public static boolean nonEmpty(Object obj) {
        return !isEmpty(obj);
    }
    
    public static Map<String, Object> transBeanToMap(Object obj) {
		if (obj == null) {
			return null;
		}

        if(obj instanceof Map){
            return (Map<String, Object>)obj;
        }

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性

                if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
                    if (getter != null) {
                        Object value = getter.invoke(obj);
                        if (value != null) {
                            map.put(key, value);
                        }
                    }
				}
			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}
		return map;
	}


    public static <T> T nvl(T t, T d) {
        return Optional.ofNullable(t).orElse(d);
    }

    public static <T> T nvl(T t, Supplier<? extends T> supplier) {
        return Optional.ofNullable(t).orElseGet(supplier);
    }

    public static <T> Boolean anyEmpty(T... ts) {
        return Stream.of(ts).anyMatch( ObjectUtil::isEmpty );
    }

    /**
     * 字符串转list
     * @param data 字符串
     * @param t    泛型class
     * @param <T>  泛型
     * @return     转化结果
     */
    public static <T> List<T> stringToList(String data, Class<T> t) {
        return new Gson().fromJson(data, TypeToken.getParameterized(ArrayList.class, t).getType());
    }

    public static <T> T trim(T t) throws Exception {

        Field[] fields = t.getClass().getDeclaredFields();

        for (int i = 0; i< fields.length; i++) {

            fields[i].setAccessible(true);
            Object f = fields[i].get(t);

            if (f instanceof String) {

                String sf = (String) f;

                if (null != sf) {
                    sf = StringUtils.isEmpty(sf.trim()) ? null: sf;
                }


                fields[i].set(t, sf);
            }
        }
        return t;
    }


    public static <T> T blank(T t) throws Exception {

        Field[] fields = t.getClass().getDeclaredFields();

        for (int i = 0; i< fields.length; i++) {

            fields[i].setAccessible(true);

            Field field = fields[i];
            Object f = fields[i].get(t);


            String classString = field.getGenericType().toString();

            if (classString.equals("class java.lang.String")) {

                String sf = (String) f;

                if (StringUtils.isEmpty(sf)) {
                    sf = " ";
                }

                fields[i].set(t, sf);
            }

            if (classString.equals("class java.math.BigDecimal")) {

                BigDecimal sf = (BigDecimal) f;
                if (null == sf) {
                    sf = new BigDecimal("0");
                }

                fields[i].set(t, sf);
            }

            if (classString.equals("class java.lang.Short")) {

                Short sf = (Short) f;
                if (null == sf) {
                    sf = 0;
                }

                fields[i].set(t, sf);
            }
        }
        return t;
    }
}
