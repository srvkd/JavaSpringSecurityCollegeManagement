
/*
Filename:GeneralServices.java

Author:Saurabh Kedia

Description: Service Layer interface  declaring methods  other than for user registration

Date Modified:31.10.2018

*/


package com.college.service.interfaces;

import java.sql.SQLException;

import com.college.core.beans.User;
import com.college.exception.CollegeException;

public interface RegistrationService {
	
	public int registerTheUser(User user) throws SQLException, CollegeException;

}
