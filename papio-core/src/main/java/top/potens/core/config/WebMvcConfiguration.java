package top.potens.core.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.potens.core.interceptor.UserAuthorizationInterceptor;

import javax.annotation.Resource;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className WebMvcConfiguration
 * @projectName web-api
 * @date 2019/8/28 20:16
 */
@SpringBootConfiguration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Resource
    private UserAuthorizationInterceptor userAuthorizationInterceptor;

    /**
     * addInterceptor()的顺序需要严格按照程序的执行的顺序
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAuthorizationInterceptor).addPathPatterns("/**");
    }
}
