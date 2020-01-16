package top.potens.core.model;

import top.potens.core.serialization.JSON;

/**
 * Created by yanshaowen on 2019/6/16.
 */
public class ApiResult<T> {
    private String code;
    private String message;
    private T data;

    public ApiResult() {
        this.code = "0";
        this.message = "suc";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
