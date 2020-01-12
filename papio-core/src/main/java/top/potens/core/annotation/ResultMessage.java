package top.potens.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className ResultMessage
 * @projectName web-api
 * @date 2019/8/18 8:52
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResultMessage {
    String value() default "";
}
