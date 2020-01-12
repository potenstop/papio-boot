package top.potens.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * Created by wenshao on 2019/7/21.
 */
public class StringUtil extends StringUtils {
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
