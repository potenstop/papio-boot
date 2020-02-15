package top.potens.redis.config;

import jodd.util.StringUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className RedissonConfiguration
 * @projectName web-api
 * @date 2019/7/23 13:19
 */
@Configuration
public class RedissonConfiguration {
    @Value("${fx.config.redisson.url}")
    private String url;

    @Value("${fx.config.redisson.password}")
    private String password;

    @Value("${fx.config.redisson.database}")
    private Integer database;

    @Bean
    public RedissonClient getRedisson() {

        if (password == null || password.length() == 0) {
            password = null;
        }
        Config config = new Config();
        config.useSingleServer()
                .setAddress(url)
                .setPassword(password).setDatabase(database)
                .setConnectionPoolSize(10)
                .setConnectionMinimumIdleSize(4)
                .setSubscriptionConnectionPoolSize(5)
                .setSubscriptionConnectionMinimumIdleSize(2);
        //添加主从配置
        // config.useMasterSlaveServers().setMasterAddress("").setPassword("").addSlaveAddress(new String[]{"",""});

        return Redisson.create(config);
    }
}
