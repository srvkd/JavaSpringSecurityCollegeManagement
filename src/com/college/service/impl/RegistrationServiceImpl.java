
/*
Filename:RegistrationServiceImpl.java

Author:Saurabh Kedia

Description: Service Layer implementation of the interfacing calling the DAO classes
 which will then connect to the database and return results ONLY for user registration

Date Modified:31.10.2018

*/

package com.college.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.college.core.beans.User;
import com.college.dao.impl.RegistrationDAOImpl;
import com.college.exception.CollegeException;
import com.college.service.interfaces.RegistrationService;


public class RegistrationServiceImpl implements RegistrationService {

	//Injecting DAO object to the service layer 
	@Autowired
	private RegistrationDAOImpl registerDao;
	
	@Override
	public int registerTheUser(User user) throws SQLException, CollegeException 
	{
		System.out.println("service method reached ");
		return registerDao.registerTheUser(user);
	}
	

}
