package top.potens.fegin.config;

import feign.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.potens.fegin.client.GatewayFeignClient;
import top.potens.log.AppLogger;

import java.util.Map;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className FilterFeignClientConfiguration
 * @projectName papio-framework
 * @date 2020/3/13 13:36
 */
@Configuration
@ConditionalOnProperty(name = "spring.profiles.active",havingValue = "dev")
public class GatewayFeignClientConfiguration {
    @Bean
    public Client feignClient() {
        AppLogger.debug("进入自定义feign client");
        return new GatewayFeignClient();
    }
}
