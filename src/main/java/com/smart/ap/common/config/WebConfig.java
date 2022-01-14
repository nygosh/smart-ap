package com.smart.ap.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.smart.ap.common.component.HttpFilterComponent;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private HttpFilterComponent httpFilterComponent;

	private static final String[] EXCLUDE_PATHS = { "/error/**" };
	private static final String[] SWAGGER_PATHS = { "/swagger-ui/**", "/swagger/**", "/swagger-resources/**", "/v3/**"  };


	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

	@Bean
	public MappingJackson2HttpMessageConverter MappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		List<MediaType> list = new ArrayList<MediaType>();
		list.add(MediaType.APPLICATION_JSON);
		list.add(MediaType.APPLICATION_XML);
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
		mappingJackson2HttpMessageConverter.setPrettyPrint(true);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new Hibernate5Module());
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
		return mappingJackson2HttpMessageConverter;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

	}

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(httpFilterComponent).addPathPatterns("/**").excludePathPatterns(EXCLUDE_PATHS).excludePathPatterns(SWAGGER_PATHS);
    }

}