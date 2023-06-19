package com.geekplus.rms.analysis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableSwagger2WebMvc
@ConditionalOnProperty(value = "knife4j.enable",havingValue = "true")
public class Knife4jConfiguration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("groupName:version 1.0")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.geekplus.rms.analysis.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("knife4j API文档")
                .description("knife4j API文档")
                .termsOfServiceUrl("http://xxxx:8086/rmsAnalysis/doc.html")
                .contact(new Contact("knife4j", "https://www.knife4j.com", null))
                .version("1.0")
                .build();
    }

}
