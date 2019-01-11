package com.stackroute.fse.capsule.moviecruiserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.stackroute.fse.capsule.moviecruiserservice.filter.JwtFilter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MovieCruiserApplication {

	private static final String PATH = "/";

	public static void main(String[] args) {
		SpringApplication.run(MovieCruiserApplication.class, args);
	}

	@Bean
	Docket movieCruiseSwagger() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
				.apis(RequestHandlerSelectors.basePackage(MovieCruiserApplication.class.getPackage().getName()))
				.paths(PathSelectors.any())
				.build()
			.apiInfo(ApiInfo.DEFAULT)
			.pathMapping(MovieCruiserApplication.PATH);
	}

	public FilterRegistrationBean<JwtFilter> registerJwtFilter() {
		final FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new JwtFilter());
		registrationBean.addUrlPatterns("/api/*");
		return registrationBean;
	}

}
