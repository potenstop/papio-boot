package top.potens.generator;

import org.junit.jupiter.api.Test;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className MybatisGeneratorTest
 * @projectName papio-framework
 * @date 2020/1/16 11:38
 */
public class MybatisGeneratorTest {
    @Test
    void run() throws Exception {
        MybatisGenerator mybatisGenerator = MybatisGenerator.getInstance();
        mybatisGenerator.setJdbcConnectionURL("jdbc:mysql://potens.top:7306/web?useSSL=false")
                .setJdbcDriverClass("com.mysql.cj.jdbc.Driver")
                .setJdbcUserId("aa")
                .setJdbcPassword("bb")
                .setModelTargetPackage("top.potens.generator.model")
                .setModelTargetProject("src/test/java")
                .setMapTargetPackage("auto")
                .setMapTargetProject("src/test/resources/mappings")
                .setClientTargetPackage("top.potens.generator.dao.db.auto")
                .setClientTargetProject("src/test/java")
                .addTable("aaa", "id", "Aa")
                .run();
    }
}
