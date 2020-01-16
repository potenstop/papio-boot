package top.potens.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import top.potens.log.HttpContext;
import top.potens.core.exception.ApiException;
import top.potens.log.AppLogger;
import top.potens.core.serialization.JSON;

import javax.servlet.http.HttpServletRequest;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className ControllerVisitAspect
 * @projectName web-api
 * @date 2019/8/18 13:35
 */
@Aspect
@Component
@Order(1001)
public class ControllerVisitAspect {

    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = HttpContext.getRequest();

        String uri = request.getRequestURI().replace(request.getContextPath(), "");
        AppLogger.info("request.getContentType() {}" , request.getContentType());
        boolean isJson = MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType()) || MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType());
        if (isJson) {
            AppLogger.info("controller-start-request uri:[{}] methodName:[{}] param:[{}]", uri, joinPoint.getSignature().getName(), JSON.toJSONStringNotEx(joinPoint.getArgs()));
        } else {
            AppLogger.info("controller-start-request uri:[{}] methodName:[{}]", uri, joinPoint.getSignature().getName());
        }
        Object result = joinPoint.proceed();
        AppLogger.info("controller-end-request uri:[{}] methodName:[{}] result:[{}]", uri, joinPoint.getSignature().getName(), JSON.toJSONStringNotEx(result));
        return result;
    }

    @AfterThrowing(
            pointcut = "pointcut()",
            throwing = "ex"
    )
    public void doAfterEx(JoinPoint joinPoint, Throwable ex) {
        HttpServletRequest request = HttpContext.getRequest();
        String uri = request.getRequestURI().replace(request.getContextPath(), "");
        if (ex instanceof ApiException) {
            AppLogger.warn("error uri:[{}] methodName:[{}]", ex, uri, joinPoint.getSignature().getName());
        } else {
            AppLogger.error("error uri:[{}] methodName:[{}]", ex, uri, joinPoint.getSignature().getName());
        }
    }

}
