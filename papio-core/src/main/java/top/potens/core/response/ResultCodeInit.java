package top.potens.core.response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;
import top.potens.core.annotation.ResultMessage;
import top.potens.core.log.AppLogger;
import top.potens.core.plugin.PackageClassesLoader;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className ResultCodeInit
 * @projectName web-api
 * @date 2019/8/18 8:53
 */
public class ResultCodeInit {

    private static final ConcurrentHashMap<String, String> resultCodeMsgMap = new ConcurrentHashMap<>();

    public static void addResultCodeDefinitionClass(Class<?> c) {
        try {
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String resultCode = String.valueOf(field.get(null));
                // 返回码定义冲突
                if (resultCodeMsgMap.containsKey(resultCode)) {
                    AppLogger.error("Result code definitions conflict! resultCode：" + resultCode);
                    System.exit(0);
                }
                ResultMessage annotation = field.getAnnotation(ResultMessage.class);
                if (null != annotation) {
                    String resultMsg = annotation.value();
                    if (null != resultMsg && !"".equals(resultMsg.trim())) {
                        resultCodeMsgMap.put(resultCode, resultMsg);
                    }
                }
            }
        } catch (Exception e) {
            AppLogger.error("Fail to add result code info!", e);
        }
    }

    public static void addResultCodeDefinitionClassByPackage(String... packageName) {
        if(packageName == null || packageName.length <= 0){
            return;
        }
        try {
            PackageClassesLoader packageClassesLoader = new PackageClassesLoader(packageName);
            Set<Class<?>> set = packageClassesLoader.getClassSet();
            for (Class c : set) {
                addResultCodeDefinitionClass(c);
            }
        } catch (Exception e) {
            AppLogger.error("addResultCodeDefinitionClassByPackage error", e);
            System.exit(0);
        }
    }
    public static String getResultMsg(String resultCode, Object... args) {
        String message = resultCodeMsgMap.getOrDefault(resultCode, StringUtils.EMPTY);
        if (StringUtils.isNotBlank(message)) {
            if (args != null && args.length > 0) {
                message = MessageFormatter.format(message, args).getMessage();
            }
        }
        return message;
    }


}
