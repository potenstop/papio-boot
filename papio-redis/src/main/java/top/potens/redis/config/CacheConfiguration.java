package top.potens.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenshao on 2019/7/21.
 */
@Configuration
@EnableCaching
public class CacheConfiguration {
    @Value("${fx.redis.cache.url}")
    private String masterUrl;

    @Value("${fx.redis.cache.port}")
    private Integer port;

    @Value("${fx.redis.cache.database}")
    private Integer database;

    @Value("${fx.redis.cache.password}")
    private String password;

    @Bean("redisCacheFactory")
    @Primary
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(masterUrl, port);
        config.setPassword(password);
        config.setDatabase(database);
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(10))
                .shutdownTimeout(Duration.ZERO)
                .build();

        return new LettuceConnectionFactory(config, clientConfig);
    }
    @Bean("redisCacheTemplate")
    @Primary
    public StringRedisTemplate redisCacheTemplate(@Qualifier("redisCacheFactory") RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(factory);
        return stringRedisTemplate;
    }
    /*@Bean
    public RedisTemplate<String, Object> redisTemplate(
            LettuceConnectionFactory lettuceConnectionFactory) throws UnknownHostException {

        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
//        // key序列化
        redisTemplate.setKeySerializer(stringSerializer);
        // value序列化
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // Hash key序列化
        redisTemplate.setHashKeySerializer(stringSerializer);
        // Hash value序列化
//        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }*/

    @Bean
    public KeyGenerator simpleKeyGenerator() {
        return (o, method, objects) -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(o.getClass().getSimpleName());
            stringBuilder.append(".");
            stringBuilder.append(method.getName());
            stringBuilder.append("[");
            for (Object obj : objects) {
                stringBuilder.append(obj.toString());
            }
            stringBuilder.append("]");

            return stringBuilder.toString();
        };
    }

    public static RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));
        // new JedisConnectionFactory()

        return redisCacheConfiguration;
    }
}
