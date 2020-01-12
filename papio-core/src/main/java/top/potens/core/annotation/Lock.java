package top.potens.core.annotation;

import top.potens.core.enums.LockModel;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className Lock
 * @projectName web-api
 * @date 2019/8/24 10:31
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {

    /**
     * 锁的模式:如果不设置,自动模式,当参数只有一个.使用 REENTRANT 参数多个 MULTIPLE
     */
    LockModel lockModel() default LockModel.AUTO;

    /**
     * 如果keys有多个,如果不设置,则使用 联锁
     */
    String[] keys() default {};

    /**
     * 锁超时时间
     */
    long lockWatchTimeout() default 0;

    /**
     * 等待加锁超时时间 -1 则表示一直等待
     */
    long attemptTimeout() default 0;

    /**
     * 单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
