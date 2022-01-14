package com.smart.ap.common.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

/**
 *
 * @author MW
 */
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

	 @Bean
	    public Docket platformApi() {
	        return new Docket(DocumentationType.OAS_30)
	        		.securityContexts(Arrays.asList(securityContext()))
	        		.securitySchemes(Arrays.asList(apiKey()))
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.inissoft.ap.controller"))
	                .paths(PathSelectors.any())
	                .build()
	                .groupName("inissoft")
	                .useDefaultResponseMessages(false)
	                .apiInfo(apiInfo());
	    }

	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("INISSOFT API")
	                .version("1.0")
	                .build();
	    }

	    @Bean
	    UiConfiguration uiConfig() {
	      return UiConfigurationBuilder.builder() //<20>
	          .deepLinking(true)
	          .displayOperationId(false)
	          .defaultModelsExpandDepth(1)
	          .defaultModelExpandDepth(1)
	          .defaultModelRendering(ModelRendering.EXAMPLE)
	          .displayRequestDuration(false)
	          .docExpansion(DocExpansion.NONE)
	          .filter(true)
	          .maxDisplayedTags(null)
	          .operationsSorter(OperationsSorter.METHOD)
	          .showExtensions(false)
	          .showCommonExtensions(false)
	          .tagsSorter(TagsSorter.ALPHA)
	          .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
	          .validatorUrl(null)
	          .build();
	    }

	    private SecurityContext securityContext() {
	        return SecurityContext.builder()
	            .securityReferences(defaultAuth())
	            .build();
	    }

	    private List<SecurityReference> defaultAuth() {
	        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
	        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	        authorizationScopes[0] = authorizationScope;
	        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
	    }

	    private ApiKey apiKey() {
	        return new ApiKey("Authorization", "X-AUTH-TOKEN", "header");
	    }
}