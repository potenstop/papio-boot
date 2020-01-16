package top.potens.log;

/**
 * Created by yanshaowen on 2019/6/15.
 */
public class App extends StandardLog {
    /**
     * 打印的文本
     */
    private String message;
    /**
     * 有异常时候的栈信息
     */
    private String stack;
    /**
     * 日志基本
     */
    private String level;
    /**
     * 类名
     */
    private String className;
    /**
     * 只是方法名称
     */
    private String methodName;
    /**
     * 完整文件名称
     */
    private String fileName;
    /**
     * 行号
     */
    private Integer line;

    /**
     * 客户端ip
     */
    private String clientIp;
    /**
     * 服务端ip
     */
    private String serviceIp;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getServiceIp() {
        return serviceIp;
    }

    public void setServiceIp(String serviceIp) {
        this.serviceIp = serviceIp;
    }
}
