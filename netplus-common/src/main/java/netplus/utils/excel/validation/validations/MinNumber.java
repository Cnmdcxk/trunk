package netplus.utils.excel.validation.validations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinNumber {

    String message() default "";

    int value() default 0;

    boolean equal() default true;

}
