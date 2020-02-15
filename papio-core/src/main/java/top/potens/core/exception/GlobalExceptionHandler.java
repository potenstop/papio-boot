package top.potens.core.exception;


import org.springframework.beans.BeansException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.potens.core.constant.RestConstant;
import top.potens.core.util.StringUtil;
import top.potens.log.AppLogger;
import top.potens.core.model.ApiResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * Created by yanshaowen on 2019/6/16.
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    //添加全局业务异常处理流程，根据需要设置需要处理的异常
    @ExceptionHandler(value = ApiException.class)
    public Object apiExceptionHandler(HttpServletRequest request,
                                           ApiException exception) throws Exception {
        return getResult(StringUtil.isEmpty(exception.getCode()) ? "0" : exception.getCode(), exception.getMessage());
    }
    private ApiResult getResult(String code, String message) {
        ApiResult result = new ApiResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    // 处理beans异常
    @ExceptionHandler(value = BeansException.class)
    public Object beansException(BeansException exception) throws Exception {
        return getResult(RestConstant.BEANS_EXCEPTION, exception.getMessage());
    }
    // 处理序列化异常
    @ExceptionHandler(value = SerializationException.class)
    public Object serializationExceptionException(BeansException exception) throws Exception {
        return getResult(RestConstant.SERIALIZATION_EXCEPTION, exception.getMessage());
    }

    // 处理Servlet异常
    @ExceptionHandler(value = ServletException.class)
    public Object servletException(ServletException exception) throws Exception {
        return getResult(RestConstant.SERVLET_EXCEPTION, exception.getMessage());
    }

    // 参数未传递异常
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Object missingServletRequestParameterException(HttpServletResponse response, HttpServletRequest request,
                                                          Exception exception) throws Exception {
        MissingServletRequestParameterException c = (MissingServletRequestParameterException) exception;
        StringBuilder message = new StringBuilder();
        message.append(c.getParameterName()).append(" is not present");
        return getResult(RestConstant.MISSING_EXCEPTION, message.toString());
    }
    @ExceptionHandler
    public Object exceptionHandler(HttpServletRequest request, Exception ex) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stacks = ex.getStackTrace();
        if (stacks.length >= 1) {
            sb.append("class: ").append(stacks[1].getClassName())
                    .append("; method: ").append(stacks[1].getMethodName())
                    .append("; line_number: ").append(stacks[1].getLineNumber());
        }
        AppLogger.error(sb.toString(), ex);
        return getResult(RestConstant.INTERNAL_SERVER_EXCEPTION, ex.getMessage());
    }

    // 数据验证失败异常 spring boot valid
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidException(HttpServletResponse response, HttpServletRequest request,
                                                  Exception exception) throws Exception {
        MethodArgumentNotValidException c = (MethodArgumentNotValidException) exception;
        List<ObjectError> errors = c.getBindingResult().getAllErrors();
        StringBuilder errorMsg = new StringBuilder();
        errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
        StringBuilder message = new StringBuilder();
        for (ObjectError error : errors) {
            message.append(((FieldError) error).getField() + ":" + ((FieldError) error).getDefaultMessage() + ";");
            break;
        }
        return getResult(RestConstant.MISSING_EXCEPTION, message.toString());
    }

    // 数据验证失败异常  spring Validated
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Object constraintViolationException(HttpServletResponse response, HttpServletRequest request,
                                               Exception exception) throws Exception {
        ConstraintViolationException e = (ConstraintViolationException) exception;
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getPropertyPath().toString() + ":" + violation.getMessage() + "\n");
            break;
        }
        return getResult(RestConstant.MISSING_EXCEPTION, strBuilder.toString());
    }



}
