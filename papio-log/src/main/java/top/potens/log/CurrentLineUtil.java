package top.potens.log;


/**
 * Created by yanshaowen on 2019/6/16.
 */
public class CurrentLineUtil {
    public static int LOG_INDEX = 3;

    public static String getFileName(StackTraceElement[]stackTraceElements, int index) {
        try {
            return stackTraceElements[index].getFileName();
        }catch (Exception e) {
            return "";
        }
    }

    public static String getClassName(StackTraceElement[]stackTraceElements, int index) {
        try {
            return stackTraceElements[index].getClassName();
        }catch (Exception e) {
            return "";
        }
    }

    public static String getMethodName(StackTraceElement[]stackTraceElements, int index) {
        try {
            return stackTraceElements[index].getMethodName();
        }catch (Exception e) {
            return "";
        }
    }

    public static int getLineNumber(StackTraceElement[]stackTraceElements, int index) {
        try {
        return stackTraceElements[index].getLineNumber();
        }catch (Exception e) {
            return 0;
        }
    }
    public static StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

}
