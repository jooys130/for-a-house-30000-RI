package com.ssafy.myhome.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

//	Swagger-UI 3.x 확인
//	http://localhost/whereismyhome/swagger-ui/index.html#/
	
	private final ServerProperties serverProperties;

    public SwaggerConfiguration(ServerProperties serverProperties) {
		super();
		this.serverProperties = serverProperties;
	}
	
    @Bean
    public Docket allApi() {
        return new Docket(DocumentationType.OAS_30)
        	.consumes(getConsumeContentTypes())
        	.produces(getProduceContentTypes())
            .groupName("all-api")
            .apiInfo(allApiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.ssafy.myhome"))
            .paths(PathSelectors.ant("/**"))
            .build()
            .useDefaultResponseMessages(false);
    }

    
    private ApiInfo allApiInfo() {
        Integer port = serverProperties.getPort();
        return new ApiInfoBuilder()
            .title("WhereIsMyHome Swagger Open API Docs")
            .description(
                "<h3>(주)전주김밥 API Reference for Developers</h3>Swagger를 이용한 REST API<br>")
            .version("1.0")
            .license("SSAFY License")
            .licenseUrl("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp")
            .termsOfServiceUrl("http://edu.ssafy.com")
            .build();
    }
    
	private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
//      consumes.add("application/xml;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

}
