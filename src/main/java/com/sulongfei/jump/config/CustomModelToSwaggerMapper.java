package com.sulongfei.jump.config;

import io.swagger.models.parameters.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2MapperImpl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Component("ServiceModelToSwagger2Mapper")
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class CustomModelToSwaggerMapper extends ServiceModelToSwagger2MapperImpl {

    @Override
    protected List<Parameter> parameterListToParameterList(List<springfox.documentation.service.Parameter> list) {
        list = list.stream().filter(p -> !"hidden".equals(p.getParamAccess())).collect(Collectors.toList());
        list = list.stream().sorted(Comparator.comparingInt(springfox.documentation.service.Parameter::getOrder)).collect(Collectors.toList());
        return super.parameterListToParameterList(list);
    }
}
