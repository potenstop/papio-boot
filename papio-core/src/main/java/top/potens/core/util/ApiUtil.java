package top.potens.core.util;

import top.potens.core.exception.ApiException;
import top.potens.core.model.ApiResult;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className ApiUtil
 * @projectName papio-framework
 * @date 2020/1/31 16:28
 */
public class ApiUtil {
    public static <T> T getData(ApiResult<T> result) {
        if (result == null) {
            throw new ApiException("400", "返回值为空");
        }
        if (!"0".equals(result.getCode())) {
            throw new ApiException(result.getCode(), result.getMessage());
        }
        return result.getData();
    }
    public static <T> T getDataNotNull(ApiResult<T> result) {
        T data = getData(result);
        if (data == null) {
            throw new ApiException("401", "data为null");
        }
        return data;
    }
}
