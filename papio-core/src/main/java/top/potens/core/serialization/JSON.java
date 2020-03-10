package top.potens.core.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import top.potens.core.exception.SerializationException;
import top.potens.log.AppLogger;

import java.io.IOException;

/**
 * Created by yanshaowen on 2019/6/15.
 */
public class JSON {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 如果为空则不输出
        // objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // 对于空的对象转json的时候不抛出错误
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 禁用序列化日期为timestamps
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 禁用遇到未知属性抛出异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 视空字符传为null
        // objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        // 低层级配置
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // 允许属性名称没有引号
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 取消对非ASCII字符的转码
        // objectMapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, false);
        // 允许出现特殊字符和转义符
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;

    }

    public static String toJSONString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e.getMessage());
        }
    }

    public static String toJSONStringNotEx(Object value) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            AppLogger.error("toJSONStringNotEx error ", e);
            return null;
        }
    }

    public static <T> T toObjectNotEx(String str, Class<T> valueType) {
        if (str == null || str.length() == 0 || valueType == null) {
            AppLogger.warn("toObjectNotEx warn params is null str:[{}] valueType:[{}]", str, valueType);
            return null;
        }
        try {
            return objectMapper.readValue(str, valueType);
        } catch (IOException e) {
            AppLogger.error("toObjectNotEx error ", e);
            return null;
        }
    }
}
