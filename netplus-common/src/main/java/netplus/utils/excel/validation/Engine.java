package netplus.utils.excel.validation;


import netplus.utils.excel.entity.EntityWrap;
import netplus.utils.excel.entity.FieldWrap;
import netplus.utils.excel.validation.validations.*;
import org.apache.commons.lang.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Engine {

    private static String[] dateFormats = new String[]{ "yyyy-MM-dd", "yyyy/MM/dd" };
    private static Pattern numberPattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");

    public static boolean validate(EntityWrap obj) {

        boolean validFlag = true;

        if (obj != null) {

            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields) {

                if (!field.getType().isAssignableFrom(FieldWrap.class))
                    continue;

                //反射得到值
                field.setAccessible(true);
                FieldWrap fieldWrap = null;

                try {
                    fieldWrap = (FieldWrap) field.get(obj);
                } catch (IllegalAccessException ix) {
                    //
                }

                if (fieldWrap == null)
                    continue;

                //验证集合
                String value = fieldWrap.getValue();

                for (Annotation v : field.getDeclaredAnnotations()) {

                    if (v.annotationType().isAssignableFrom(Required.class)) {
                        Required required = (Required) v;
                        if (StringUtils.isBlank(value)) {
                            validFlag = setInvalid(obj, fieldWrap, required.message());
                            break;
                        }
                    } else if (v.annotationType().isAssignableFrom(Regex.class)) {
                        Regex regex = (Regex) v;
                        if (StringUtils.isNotBlank(value)) {
	                        Pattern pattern = Pattern.compile(regex.value(), Pattern.CASE_INSENSITIVE);
	                        Matcher matcher = pattern.matcher(value);
	                        if (!matcher.matches()) {
	                            validFlag = setInvalid(obj, fieldWrap, regex.message());
	                            break;
	                        }
                        }
                    } else if (v.annotationType().isAssignableFrom(MinLength.class)) {
                        MinLength length = (MinLength) v;
                        if (value == null || value.length() < length.value()) {
                            validFlag = setInvalid(obj, fieldWrap, length.message());
                            break;
                        }
                    } else if (v.annotationType().isAssignableFrom(MinNumber.class)) {
                        MinNumber minNumber = (MinNumber) v;
                        if (StringUtils.isNotBlank(value)) {
                        	if(!isNumber(value)){
                            	validFlag = setInvalid(obj, fieldWrap, "请输入有效的数字");
                                break;
                            }
                            if (minNumber.equal() && Float.parseFloat(value) <= minNumber.value()) {
                                validFlag = setInvalid(obj, fieldWrap, minNumber.message());
                                break;
                            }else if (!minNumber.equal() && Float.parseFloat(value) < minNumber.value()) {
                                validFlag = setInvalid(obj, fieldWrap, minNumber.message());
                                break;
                            }
						}
                    } else if (v.annotationType().isAssignableFrom(MaxLength.class)) {
                        MaxLength maxLength = (MaxLength) v;
                        if (value != null && value.length() > maxLength.value()) {
                            validFlag = setInvalid(obj, fieldWrap, maxLength.message());
                            break;
                        }
                    } else if (v.annotationType().isAssignableFrom(MaxNumber.class)) {
                        MaxNumber maxNumber = (MaxNumber) v;
                        if (StringUtils.isNotBlank(value)) {
                            if(!isNumber(value)){
                            	validFlag = setInvalid(obj, fieldWrap, "请输入有效的数字");
                                break;
                            }
	                        if (Float.parseFloat(value) > maxNumber.value()) {
	                            validFlag = setInvalid(obj, fieldWrap, maxNumber.message());
	                            break;
	                        }
                        }
                    } else if (v.annotationType().isAssignableFrom(IsDate.class)) {
                        IsDate isDate = (IsDate) v;
                        if (StringUtils.isNotBlank(value)) {
	                        boolean valid = false;
	                        for (String f : dateFormats) {
	                            try {
	                                SimpleDateFormat format = new SimpleDateFormat(f);
	                                format.parse(value);
	                                valid = true;
	                                break;
	                            } catch (ParseException e) {
	                                //e.printStackTrace();
	                            }
	                        }
	                        if (!valid) {
	                            validFlag = setInvalid(obj, fieldWrap, isDate.message());
	                            break;
	                        }
                        }
                    } else if (v.annotationType().isAssignableFrom(InRange.class)) {
                    	if (StringUtils.isNotBlank(value)) {
	                        InRange range = (InRange) v;
	                        String[] arr = range.value().split(",");
	
	                        boolean contains = false;
	                        for (String a : arr) {
	                            if (a.length() > 0 && a.equals(value)) {
	                                contains = true;
	                                break;
	                            }
	                        }
	
	                        if (!contains) {
	                            validFlag = setInvalid(obj, fieldWrap, range.message());
	                            break;
	                        }
                    	}
                    }else if(v.annotationType().isAssignableFrom(Forbid.class)){
                       if(StringUtils.isBlank(value) /*|| !StringUtil.isForbidden(value)*/){
                           validFlag = true;
                       }else {
                           validFlag = setInvalid(obj, fieldWrap, ((Forbid) v).message());
                           break;
                       }

                    }else if (v.annotationType().isAssignableFrom(YesOrNo.class)) {

                        if(StringUtils.isBlank(value)){
                            validFlag = true;
                        }else {

                            if (!value.equals("是") && !value.equals("否")) {
                                validFlag = setInvalid(obj, fieldWrap, ((YesOrNo) v).message());
                                break;

                            }else{
                                validFlag = true;
                            }

                        }
                    }
                }
            }
        }
        else{
            validFlag = false;
        }

        return validFlag;
    }


    private static boolean setInvalid(EntityWrap obj, FieldWrap fieldWrap, String message) {
        //
        fieldWrap.setMessage(message);

        //
        obj.setIsValid(false);
        obj.setMessage(message);

        return false;
    }

    // 普通验证使用，验证前台提交数据
    public static List<String>  validate(Object o) {
        List<String> errMsg = new ArrayList<>();

        if (o == null) {
            return errMsg;
        }

        Field[] fields = o.getClass().getDeclaredFields();

        for (Field field : fields) {

            //反射得到值
            field.setAccessible(true);
            String value = null;

            try {
                value = field.get(o).toString();
            } catch (Exception ix) {
                //
            }

            if (value == null)
                continue;

            //验证集合
            for (Annotation v : field.getDeclaredAnnotations()) {

                if (v.annotationType().isAssignableFrom(Required.class)) {
                    Required required = (Required) v;
                    if (StringUtils.isEmpty(value)) {
                        errMsg.add(required.message());
                        break;
                    }
                } else if (v.annotationType().isAssignableFrom(Regex.class)) {
                    Regex regex = (Regex) v;
                    if (StringUtils.isNotBlank(value)) {
                        Pattern pattern = Pattern.compile(regex.value(), Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(value);
                        if (!matcher.matches()) {
                            errMsg.add(regex.message());
                            break;
                        }
                    }
                } else if (v.annotationType().isAssignableFrom(MinLength.class)) {
                    MinLength length = (MinLength) v;
                    if (value == null || value.length() < length.value()) {
                        errMsg.add(length.message());
                        break;
                    }
                } else if (v.annotationType().isAssignableFrom(MinNumber.class)) {
                    MinNumber minNumber = (MinNumber) v;
                    if (StringUtils.isNotBlank(value)) {
                    	if(!isNumber(value)){
                    		errMsg.add("请输入有效的数字");
                            break;
                        }
                        if (minNumber.equal() && Float.parseFloat(value) <= minNumber.value()) {
                            errMsg.add(minNumber.message());
                            break;
                        }else if (!minNumber.equal() && Float.parseFloat(value) < minNumber.value()) {
                            errMsg.add(minNumber.message());
                            break;
                        }
                    }
                } else if (v.annotationType().isAssignableFrom(MaxLength.class)) {
                    MaxLength maxLength = (MaxLength) v;
                    if (value != null && value.length() > maxLength.value()) {
                        errMsg.add(maxLength.message());
                        break;
                    }
                } else if (v.annotationType().isAssignableFrom(MaxNumber.class)) {
                    MaxNumber maxNumber = (MaxNumber) v;
                    if (StringUtils.isNotBlank(value)) {
                    	if(!isNumber(value)){
                    		errMsg.add("请输入有效的数字");
                            break;
                        }
                        if (Float.parseFloat(value) > maxNumber.value()) {
                            errMsg.add(maxNumber.message());
                            break;
                        }
                    }
                } else if (v.annotationType().isAssignableFrom(IsDate.class)) {
                    IsDate isDate = (IsDate) v;
                    if (StringUtils.isNotBlank(value)) {
                        boolean valid = false;
                        for (String f : dateFormats) {
                            try {
                                SimpleDateFormat format = new SimpleDateFormat(f);
                                format.parse(value);
                                valid = true;
                                break;
                            } catch (ParseException e) {
                                //e.printStackTrace();
                            }
                        }
                        if (!valid) {
                            errMsg.add(isDate.message());
                            break;
                        }
                    }
                } else if (v.annotationType().isAssignableFrom(InRange.class)) {
                    if (StringUtils.isNotBlank(value)) {
                        InRange range = (InRange) v;
                        String[] arr = range.value().split(",");

                        boolean contains = false;
                        for (String a : arr) {
                            if (a.length() > 0 && a.equals(value)) {
                                contains = true;
                                break;
                            }
                        }

                        if (!contains) {
                            errMsg.add(range.message());
                            break;
                        }
                    }
                }else if (v.annotationType().isAssignableFrom(Forbid.class)) {
                    if(StringUtils.isNotBlank(value) /*&& StringUtil.isForbidden(value)*/){
                        errMsg.add(((Forbid) v).message());
                        break;
                    }
                }
            }
        }

        return errMsg;
    }
    
    /**
     * 验证是否数字
     * @param value
     * @return
     */
    public static boolean isNumber(String value){
    	 Matcher isNum = numberPattern.matcher(value);
    	 return isNum.matches();
    }
}
