package top.potens.core.context;

import jodd.util.StringUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenshao on 2019/6/15.
 */
public class HttpContext {
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new RuntimeException("requestAttributes is null");
        }
        return requestAttributes.getRequest();
    }
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new RuntimeException("requestAttributes is null");
        }
        return requestAttributes.getResponse();
    }
    public static String getClientIp() {
        try {
            HttpServletRequest request = HttpContext.getRequest();
            String ip = request.getHeader("X-Forwarded-For");
            if (StringUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
                //多次反向代理后会有多个ip值，第一个ip才是真实ip
                int index = ip.indexOf(",");
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            }
            ip = request.getHeader("X-Real-IP");
            if (StringUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            }
            return request.getRemoteAddr();
        } catch (Exception ex) {
            return "";
        }
    }

    public static String getServerIp() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            return request.getLocalAddr();
        }
        return "";

    }

    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {

        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

}
