package top.potens.core.annotation;

import java.lang.annotation.*;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className UserAuthToken
 * @projectName web-api
 * @date 2019/8/28 17:55
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserAuthToken {
}
