package netplus.utils.excel.validation.validations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Forbid {

    String message() default "输入内容包含国家违禁词，请重新编辑";

}
