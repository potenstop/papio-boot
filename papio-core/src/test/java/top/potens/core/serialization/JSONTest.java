package top.potens.core.serialization;


import org.junit.jupiter.api.Test;
import top.potens.core.model.TokenUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className JSONTest
 * @projectName papio-framework
 * @date 2020/2/15 22:20
 */
class JSONTest {

    @Test
    void toObject() {
        TokenUser tokenUser = JSON.toBeanNotEx("{\"userId\": 1, \"username\": \"111\"}", TokenUser.class);
        assertNotNull(tokenUser);
        assertEquals(1L, (long) tokenUser.getUserId());
        assertEquals("111", tokenUser.getUsername());
    }

    @Test
    void toObjectList() {
        List<TokenUser> tokenUserList = JSON.toBeanList("[{\"userId\": 1, \"username\": \"111\"}]", TokenUser.class);
        assertNotNull(tokenUserList);
        assertEquals(1L, (long) tokenUserList.get(0).getUserId());
        assertEquals("111", tokenUserList.get(0).getUsername());
    }
}