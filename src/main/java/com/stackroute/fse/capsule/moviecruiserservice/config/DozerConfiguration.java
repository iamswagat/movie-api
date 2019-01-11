package com.stackroute.fse.capsule.moviecruiserservice.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfiguration {
	
	/**
	 * Minimal dozer configuration
	 * 
	 * @return Dozer 
	 */
	@Bean( name = "org.dozer.Mapper" )
	public DozerBeanMapper dozerBean() {
		return new DozerBeanMapper();
	}
}
