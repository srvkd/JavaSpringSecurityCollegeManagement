/*
FileName:RegistrationDAO.java

Author:Saurabh Kedia

Description: Interface having declaration of method which does the registration of a new users

Date Modified:31.10.2018

*/


package com.college.dao.interfaces;

import java.sql.SQLException;

import com.college.core.beans.User;
import com.college.exception.CollegeException;

public interface RegistrationDAO {

	
	// returns 1 if the registration was successful having User Bean as the parameter. Also throws Exception
	public int registerTheUser(User user) throws SQLException, CollegeException;
}
