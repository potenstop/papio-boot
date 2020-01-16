package top.potens.log;


import org.slf4j.Logger;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.boot.logging.LogLevel;

/**
 * Created by yanshaowen on 2019/6/15.
 */
public class AppLogger {
    private static final Logger appLog = new LogWrapper("AppLoggerFile");
    private static final Logger console = new LogWrapper("AppLoggerConsole");

    public static void info(String message, Object... objects) {
        appLog.info(LogLoaderFactory.newAppLog(LogLevel.INFO, CurrentLineUtil.getStackTrace(), message).toString(), objects);
        console.info(message, objects);
    }

    public static void info(String message, Throwable throwable, Object... objects) {
        appLog.info(LogLoaderFactory.newAppLog(LogLevel.INFO, CurrentLineUtil.getStackTrace(), message, throwable).toString(), objects);
        String formatString = MessageFormatter.format(message, objects).getMessage();
        console.info(formatString, throwable);
    }

    public static void debug(String message, Object... objects) {
        appLog.debug(LogLoaderFactory.newAppLog(LogLevel.DEBUG, CurrentLineUtil.getStackTrace(), message).toString(), objects);
        console.debug(message, objects);
    }

    public static void debug(String message, Throwable throwable, Object... objects) {
        appLog.debug(LogLoaderFactory.newAppLog(LogLevel.DEBUG, CurrentLineUtil.getStackTrace(), message, throwable).toString(), objects);
        String formatString = MessageFormatter.format(message, objects).getMessage();
        console.debug(formatString, throwable);
    }

    public static void error(String message, Object... objects) {
        appLog.debug(LogLoaderFactory.newAppLog(LogLevel.ERROR, CurrentLineUtil.getStackTrace(), message).toString(), objects);
        console.error(message, objects);
    }

    public static void error(String message, Throwable throwable, Object... objects) {
        appLog.error(LogLoaderFactory.newAppLog(LogLevel.ERROR, CurrentLineUtil.getStackTrace(), message, throwable).toString(), objects);
        String formatString = MessageFormatter.format(message, objects).getMessage();
        console.error(formatString, throwable);
    }
    public static void warn(String message, Object... objects) {
        appLog.warn(LogLoaderFactory.newAppLog(LogLevel.WARN, CurrentLineUtil.getStackTrace(), message).toString(), objects);
        console.error(message, objects);
    }

    public static void warn(String message, Throwable throwable, Object... objects) {
        appLog.warn(LogLoaderFactory.newAppLog(LogLevel.WARN, CurrentLineUtil.getStackTrace(), message, throwable).toString(), objects);
        String formatString = MessageFormatter.format(message, objects).getMessage();
        console.warn(formatString, throwable);
    }
}
