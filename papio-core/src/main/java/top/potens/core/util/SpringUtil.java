package top.potens.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className SpringUtil
 * @projectName web-api
 * @date 2019/8/29 18:46
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext arg) throws BeansException {
        applicationContext = arg;
    }

    /**
     * 根据beanId获取实体对象
     *
     * @param beanId
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanId, Class<T> clazz) {
        return applicationContext.getBean(beanId, clazz);
    }

    /**
     * 根据类型获取实体对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}