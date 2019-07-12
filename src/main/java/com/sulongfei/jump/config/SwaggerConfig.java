package com.sulongfei.jump.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
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
    private static List<Parameter> parameters;
    private static List<ResponseMessage> responseMessageList;

    static {
        parameters = new ArrayList<Parameter>() {
            {
                add(new ParameterBuilder().name("access_token").description("query带上access_token").modelRef(new ModelRef("String")).parameterType("query").required(true).build());
                add(new ParameterBuilder().name("remoteClubId").description("门店ID").modelRef(new ModelRef("long")).parameterType("path").required(true).build());
                add(new ParameterBuilder().name("saleId").description("推广员ID").modelRef(new ModelRef("long")).parameterType("path").required(true).build());
                add(new ParameterBuilder().name("saleType").description("推广员类型").modelRef(new ModelRef("int")).parameterType("path").required(true).build());

            }
        };
        responseMessageList = new ArrayList<ResponseMessage>() {
            {
                add(new ResponseMessageBuilder().code(200).message("OK").build());
                add(new ResponseMessageBuilder().code(201).message("Created").build());
                add(new ResponseMessageBuilder().code(401).message("Unauthorized").build());
                add(new ResponseMessageBuilder().code(403).message("Forbidden").build());
                add(new ResponseMessageBuilder().code(404).message("Not Found").build());
                add(new ResponseMessageBuilder().code(1000).message("通用错误码（自定义）").build());
                add(new ResponseMessageBuilder().code(2000).message("权限错误码（自定义）").build());
                add(new ResponseMessageBuilder().code(3000).message("非法参数码（自定义）").build());
                add(new ResponseMessageBuilder().code(4000).message("其他异常码（自定义）").build());
                add(new ResponseMessageBuilder().code(5000).message("账号异常码（自定义）").build());
            }
        };
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
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters)
                .globalResponseMessage(RequestMethod.GET,responseMessageList)
                .globalResponseMessage(RequestMethod.POST,responseMessageList)
                .globalResponseMessage(RequestMethod.PUT,responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE,responseMessageList)
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
