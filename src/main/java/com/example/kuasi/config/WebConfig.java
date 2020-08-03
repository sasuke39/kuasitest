package com.example.kuasi.config;


import com.example.kuasi.access.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;


@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Resource
	AccessInterceptor accessInterceptor;
	

	/**
	 * 注册拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//注册
		InterceptorRegistration registration = registry.addInterceptor( accessInterceptor);
		//拦截所有路径
		registration.addPathPatterns("/**");
	}
	
}
