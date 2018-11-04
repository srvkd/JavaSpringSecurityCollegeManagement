
/*
Filename:SpringConfigurator.java

Author:Saurabh Kedia

Description: Spring Config File which initiates the bean to be stored in the spring factory.


Date Modified:31.10.2018

*/


package com.college.spring.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.college.authorize.SecurityCheck;
import com.college.dao.impl.GeneralDAOImpl;
import com.college.dao.impl.RegistrationDAOImpl;
import com.college.interceptor.RequestInterceptor;
import com.college.service.impl.GeneralServiceImpl;
import com.college.service.impl.RegistrationServiceImpl;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.college")  // scan packages starting with com.college.
public class SpringConfigurator extends WebMvcConfigurerAdapter {
	
	
	 @Override
	    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	        configurer.enable();
	    }
	 
	 
	 // Registering the RequestInterceptor.java  to the Spring Application
	 
	 @Override
	public void addInterceptors(InterceptorRegistry registry) {
		 registry.addInterceptor(new RequestInterceptor());
		 
	}

	 // Initialing datasource bean to the spring factory 
	@Bean(name="datasource")
	 public DataSource getDataSource()
		{
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	    driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
	    driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/project");
	    driverManagerDataSource.setUsername("root");
	    driverManagerDataSource.setPassword("");
	    return driverManagerDataSource;
		}
	 
	
	// Initializing GeneralServiceImpl  interface implementation
	@Bean(name="genService")
	public GeneralServiceImpl genService()
	{
		return new GeneralServiceImpl();
	}
	

	// Initializing GeneralDAOImpl  interface implementation
	@Bean(name="genDAO")
	public GeneralDAOImpl genDAO()
	{
		return new GeneralDAOImpl();
	}
	

	// Initializing RegistrationDAOImpl  interface implementation
	@Bean(name="registerDao")
	public RegistrationDAOImpl registerTheUser()
	{
		return new RegistrationDAOImpl();
	}
	

	// Initializing RegistrationServiceImpl  interface implementation
	@Bean(name="service")
	public RegistrationServiceImpl registerService()
	{
		return new RegistrationServiceImpl();
	}
	

	// Initializing ViewResolver for adding prefix and suffix to the views
	
	 @Bean
	    public InternalResourceViewResolver jspViewResolver() {
	        InternalResourceViewResolver bean = new InternalResourceViewResolver();
	        bean.setPrefix("/WEB-INF/views/");
	        bean.setSuffix(".jsp");
	        return bean;
	    }

}
