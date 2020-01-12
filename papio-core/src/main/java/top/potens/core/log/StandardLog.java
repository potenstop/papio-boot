package top.potens.core.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import top.potens.core.serialization.JSON;

/**
 * Created by wenshao on 2019/6/15.
 */
public class StandardLog {
    /**
     * 版本号
     */
    private String version;
    /**
     * 打印时间
     */
    private String time;
    /**
     * 日志类型
     */
    private String type;
    /**
     * 请求跟踪记录id
     */
    private String requestId;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 当前的环境
     */
    private String env;

    /**
     * 线程id
     */
    private Long threadId;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        return JSON.toJSONString(this);
    }

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }
}
