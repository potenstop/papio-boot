package top.potens.core.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import top.potens.core.constant.RestConstant;
import top.potens.core.constant.TokenConstant;
import top.potens.core.context.HttpContext;
import top.potens.core.exception.ApiException;
import top.potens.core.model.TokenUser;

import javax.servlet.http.HttpServletRequest;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className ControllerTokenAspect
 * @projectName web-api
 * @date 2019/8/29 10:37
 */
@Aspect
@Component
@Slf4j
@Order(1000)
public class ControllerTokenAspect {
    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = HttpContext.getRequest();
        //1.获取到所有的参数值的数组
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //2.获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();
        Class[] parameterTypes = methodSignature.getParameterTypes();
        for (int i = 0, len = parameterNames.length; i < len; i++) {
            Class parameterType = parameterTypes[i];
            if (TokenUser.class.equals(parameterType)) {
                TokenUser tokenUser = (TokenUser) request.getSession().getAttribute(TokenConstant.REQUEST_CURRENT_KEY);
                if (tokenUser == null) {
                    throw new ApiException(RestConstant.INTERNAL_SERVER_EXCEPTION, "controller define tokenUser, but session not found tokenUser");
                }
                args[i] = tokenUser;

            }
        }
        return joinPoint.proceed(args);
    }
}