/*
FileName:GeneralDAO.java

Author:Saurabh Kedia

Description: Interface having declaration of method which does the operations other than registration. 

Date Modified:31.10.2018

*/


package com.college.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.college.core.beans.SecurityQuestion;
import com.college.core.beans.User;
import com.college.exception.CollegeException;

public interface GeneralDAO {

	//change password by username
	public int changeThePassword(String username,String password,String oldPassword) throws CollegeException;

	//fetch user details by username
	public User fetchUserDetails(String username) throws CollegeException;

	//update the user details 
	public int updateUser(User user) throws CollegeException;
	

	// fetch list of all users 
	public List<User> viewAllUsers() throws CollegeException;
	
	//delete user by its userId
	public int deleteThisUser(int id) throws CollegeException;
	
	//Find a user by its username 
	public User findThisUser(String username);
	
	// Return all the security question by the user's username 
	public SecurityQuestion getSecurityQuestion(String username) throws CollegeException;
	
	//returns true if the user entered security answere matches  from the answer in the database , else false 
	public boolean verifyQuestion(SecurityQuestion question,String username);
	
	// return list of users by their firstname 
	public List<User> searchUserByName(String name) throws SQLException, CollegeException;

	int resetThePassword(String username, String password)
			throws CollegeException;
}

