package netplus.component.entity.api;


import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})// 可用在方法名上
@Retention(RetentionPolicy.RUNTIME)// 运行时有效
@Documented
public @interface HttpLog {

    String description()  default "";
}
