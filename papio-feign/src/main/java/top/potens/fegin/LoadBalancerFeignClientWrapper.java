package top.potens.fegin;

import feign.Client;
import feign.Request;
import feign.Response;

import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;
import top.potens.log.AppLogger;

import java.net.URL;


/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className LoadBalancerFeignClientWrapper
 * @projectName papio-framework
 * @date 2020/3/13 13:58
 */
public class LoadBalancerFeignClientWrapper implements Client {

    private LoadBalancerFeignClient loadBalancerFeignClient;
    private String endApiGatewayHost;

    public LoadBalancerFeignClientWrapper(Client delegate, CachingSpringLoadBalancerFactory lbClientFactory, SpringClientFactory clientFactory, String endApiGatewayHost) {

        this.endApiGatewayHost = endApiGatewayHost;
        loadBalancerFeignClient = new LoadBalancerFeignClient(delegate, lbClientFactory, clientFactory);
    }


    @Override
    public Response execute(Request request, Request.Options options) {
        AppLogger.debug("执行自定义负载均衡feign");
        try {
            URL urlSource = new URL(request.url());
            Request newRequest = Request.create(request.httpMethod(), endApiGatewayHost + urlSource.getFile(), request.headers(), request.requestBody());
            return loadBalancerFeignClient.execute(newRequest, options);
        } catch (Exception e) {
            AppLogger.warn("封装feignClient组件异常，url:%s，error:", e, request.url());
            throw new RuntimeException(e.getMessage());
        }
    }

}
