
/*
Filename:GeneralServices.java

Author:Saurabh Kedia

Description: Service Layer interface  declaring methods  ONLY for user registration

Date Modified:31.10.2018

*/

package com.college.service.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.college.core.beans.SecurityQuestion;
import com.college.core.beans.User;
import com.college.exception.CollegeException;

public interface GeneralServices {

	public int changePassword(String username,String Password,String oldPassword) throws CollegeException;
	
	public User fetchUserDetails(String username) throws CollegeException;
	
	public int updateUser(User user) throws CollegeException;
	

	int resetThePassword(String username, String password)
			throws CollegeException;
	
	public int deleteUser(int id) throws CollegeException;
	
	public List<User> viewAllUsers() throws CollegeException;
	
	public User findThisUser(String username);
	
	public SecurityQuestion getSecurityQuestion(String username) throws CollegeException;
	
	public boolean verifyQuestion(SecurityQuestion question,String username);
	
	public List<User> searchUserByName(String name) throws SQLException, CollegeException;
	
	
}
