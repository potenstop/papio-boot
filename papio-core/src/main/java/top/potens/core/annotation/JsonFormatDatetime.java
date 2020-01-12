package top.potens.core.annotation;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className JsonFormatDatetime
 * @projectName web-api
 * @date 2019/12/10 17:27
 */
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
public @interface JsonFormatDatetime {
}
