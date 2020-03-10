package top.potens.core.config;

import com.ctrip.framework.foundation.Foundation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.potens.core.model.TokenUser;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yanshaowen on 2019/6/15.
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    private String appId = Foundation.app().getAppId();

    @Value("${server.port:8080}")
    private Integer port;

    //不支持IE浏览器
    @Bean
    public Docket buildDocket() {
        ParameterBuilder plateformParameterBuilder = new ParameterBuilder();
        plateformParameterBuilder.name("platform").parameterType("header")
                .defaultValue("10").description("平台").modelRef(new ModelRef("string"))
                .required(false).build();
        ParameterBuilder tokenParameterBuilder = new ParameterBuilder();
        tokenParameterBuilder.name("token").parameterType("header")
                .defaultValue("token").description("token").modelRef(new ModelRef("string"))
                .required(false).build();
        ParameterBuilder tokenBase64ParameterBuilder = new ParameterBuilder();

        tokenBase64ParameterBuilder.name("token-base64").parameterType("header")
                .defaultValue("token-base64").description("token-base64").modelRef(new ModelRef("string"))
                .required(false).build();
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(plateformParameterBuilder.build());
        aParameters.add(tokenParameterBuilder.build());
        aParameters.add(tokenBase64ParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf()).globalOperationParameters(aParameters)
                .ignoredParameterTypes(TokenUser.class)
                .directModelSubstitute(Byte.class, Integer.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.potens"))
                .paths(PathSelectors.any())
                .build();


    }


    private ApiInfo buildApiInf() {
        return new ApiInfoBuilder()
                .title(this.appId + "接口文档")
                .description(this.appId + "相关接口的文档")
                .termsOfServiceUrl("http://127.0.0.1:" + port)
                .version("2.0")
                .build();
    }

}