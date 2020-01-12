package top.potens.core.log;

import jodd.util.StringUtil;
import org.springframework.boot.logging.LogLevel;
import top.potens.core.context.HttpContext;
import top.potens.core.util.CurrentLineUtil;
import top.potens.core.util.DateUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by wenshao on 2019/6/15.
 */
public class LogLoaderFactory {
    private static String env;
    private static String appName;
    private static String packageName = "top.potens.core.log";
    private static final String REQUEST_ID = "request-id";


    private static String getRequestId(HttpServletRequest request) {
        try {

            String traceId = request.getHeader(REQUEST_ID) != null ? request.getHeader(REQUEST_ID) : "";
            if (StringUtil.isEmpty(traceId)) {
                traceId = request.getAttribute(REQUEST_ID) != null ? request.getAttribute(REQUEST_ID).toString() : "";
            }
            return traceId;
        } catch (Exception e) {
            return "";
        }

    }
    private static long getCurrentThreadId() {
        return Thread.currentThread().getId();
    }

    private static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }


    public static App newAppLog(LogLevel logLevel, StackTraceElement[] stackTraceElements, String message) {
        return newAppLog(logLevel, stackTraceElements, message, null);
    }
    public static App newAppLog(LogLevel logLevel, StackTraceElement[] stackTraceElements, String message, Throwable throwable) {

        App log = new App();
        log.setEnv("");
        log.setVersion("1.0");
        log.setTime(DateUtil.getDateTime());
        HttpServletRequest request = HttpContext.getRequest();
        if (request != null) {
            log.setRequestId(getRequestId(request));
            log.setServiceIp(HttpContext.getServerIp());
            log.setClientIp(HttpContext.getClientIp());
        }
        log.setThreadId(getCurrentThreadId());
        log.setMethodName(CurrentLineUtil.getMethodName(stackTraceElements, CurrentLineUtil.LOG_INDEX));
        log.setLine(CurrentLineUtil.getLineNumber(stackTraceElements, CurrentLineUtil.LOG_INDEX));
        log.setClassName(CurrentLineUtil.getClassName(stackTraceElements, CurrentLineUtil.LOG_INDEX));
        log.setFileName(CurrentLineUtil.getFileName(stackTraceElements, CurrentLineUtil.LOG_INDEX));
        log.setLevel(logLevel.name());
        log.setMessage(message);
        if (throwable != null) {
            log.setStack(getStackTrace(throwable));
        }
        return log;
    }

}
