package top.potens.core.model;

import java.io.Serializable;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className TokenUser
 * @projectName web-api
 * @date 2019/8/28 19:22
 */
public class TokenUser implements Serializable {
    private static final long serialVersionUID = -2095916884810199532L;

    private Integer userId;
    private String username;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
