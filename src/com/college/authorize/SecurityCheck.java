/*
FileName:SecurityCheck.java

Author:Saurabh Kedia

Description: This is the class responsible for enabling the Authentication and Authorization which is provided by Spring Security
Framework. 

 
Date Modified:31.10.2018

*/


package com.college.authorize;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityCheck extends WebSecurityConfigurerAdapter{

	// Injecting datasource JDBC connection 
	@Autowired
	private DataSource dataSource;
	
	// BCryptPasswordEncoder for authentication purpose to calculate the hash of the password 
	
	private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
	
	// Query to fetch the username,password, accountstatus 

	private String usernameSearch="select username,password,account_status from user where username=?";
	
	
	//query to find the user role by username 
	private String roleSearch="select username,usertype from user where username=?";
	
	
	
	// Configure authentication with JDBC connection and check the user's password and role from database. 
	@Override
	protected void configure(AuthenticationManagerBuilder authenticate)
			throws Exception {
		
		//  injecting  hashed password to the authenticator 
		
		authenticate.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder).usersByUsernameQuery(usernameSearch).
		authoritiesByUsernameQuery(roleSearch);
		
	}

	
	// Provide authorization to the URLs and resources based on the roles of the user 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		final String loginUrl="/login"; 
		
		// permitting the URLs to all the users 
		
		http.authorizeRequests().
		antMatchers("/","/register","/mycss.css","/verifyOTP","/homePage",
				"/accountRecovery","securityQuestion","/errorOccured",
				"/registerTheUser",
				loginUrl,"/logout","/findUser","/verifyTheOTP","/verifySecurityQuestions","/resetUserPassword","/resetPassword")
		.permitAll();
		
		// permitting access only to the ADMIN role for deleteUser page 
		
		http.authorizeRequests().
		antMatchers("/deleteUser").hasAnyRole("ADMIN").
		anyRequest().authenticated().and().formLogin().loginPage(loginUrl).usernameParameter("username")
	      .passwordParameter("password")
	      .and().exceptionHandling().accessDeniedPage("/Access_Denied");
		
		// permitting access to only the logged in users having roles 
		
		http.authorizeRequests().
		antMatchers("/changePassword","/search","searchThisUser","/updateDetails").hasAnyRole("USER","ADMIN").
		anyRequest().authenticated().and().formLogin().loginPage(loginUrl).usernameParameter("username")
	      .passwordParameter("password")
	      .and().exceptionHandling().accessDeniedPage("/Access_Denied");
		
		
	}


	
}
