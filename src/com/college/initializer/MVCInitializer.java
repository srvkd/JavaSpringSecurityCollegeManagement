/*
FileName:MVCInitializer.java

Author:Saurabh Kedia

Description: Class responsible for initializing the SPRING MVC framework. 

Date Modified:31.10.2018

*/



package com.college.initializer;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.college.spring.config.SpringConfigurator;

public class MVCInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
 
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { SpringConfigurator.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
 
}