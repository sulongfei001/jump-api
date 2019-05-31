package com.sulongfei.jump.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger 接口文档配置
 *
 * @author siguiyang
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static List<Parameter> parameters = new ArrayList<>();

    static {
        ParameterBuilder builder = new ParameterBuilder();
        builder.defaultValue("")
                .description("用户令牌")
                .modelRef(new ModelRef("String"))
                .parameterType("query")
                .name("access_token")
                .required(true)
                .defaultValue("")
                .description("所在门店")
                .modelRef(new ModelRef("long"))
                .parameterType("body")
                .name("remote_club_id")
                .required(true)
                .build();
        parameters.add(builder.build());
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true)
                .groupName("jump-api")
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(true)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sulongfei.jump.web.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("跳一跳 API 接口")//大标题
                .description("酒吧俱乐部 跳一跳 API 接口，所有接口返回JSON格式")//详细描述
                .version("1.0")//版本
                .termsOfServiceUrl("http://www.baidu.com/")
                .contact(new Contact("苏龙飞", "", ""))//作者
                // .license("Apache 2.0")
                // .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

}
