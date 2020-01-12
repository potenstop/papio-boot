package top.potens.core.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import top.potens.core.exception.SerializationException;
import top.potens.core.log.AppLogger;

/**
 * Created by wenshao on 2019/6/15.
 */
public class JSON {
    public static String toJSONString(Object value) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e.getMessage());
        }
    }

    public static String toJSONStringNotEx(Object value) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            AppLogger.error("toString error ", e);
            return "";
        }
    }

}
