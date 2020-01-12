package top.potens.core.constant;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className TokenConstant
 * @projectName web-api
 * @date 2019/8/28 19:09
 */
public class TokenConstant {
    /**
     * redis存储token设置的过期时间 一天
     */
    public static final Integer TOKEN_EXPIRE_TIME = 60 * 60 * 24;

    /**
     * session的attr
     */
    public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";

    /**
     * token name
     */
    public static final String TOKEN_NAME = "token";
    /**
     * token key
     */
    public static final String TOKEN_REDISSION_NAME = "token:user:";
    /**
     * token key
     */
    public static final String USER_TOKEN = "user:token:";
}
