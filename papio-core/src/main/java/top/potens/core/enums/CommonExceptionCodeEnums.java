package top.potens.core.enums;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className CommonExceptionCodeEnums
 * @projectName web-api
 * @date 2019/12/10 20:28
 */
public enum CommonExceptionCodeEnums {
    // 记录未找到
    RECODE_NOT_FOUND("600", "记录未找到"),
    // 记录已被删除
    RECODE_IS_DELETE("601", "记录已被删除"),
    ;


    private String message;
    private String code;
    CommonExceptionCodeEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
