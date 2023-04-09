package netplus.utils.excel.validation.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lhp on 2017/6/27.
 * excel 下载的列名
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OutCellName {

    String name() default "";

    boolean isTime() default false;
    
    int index() default -1;

}
