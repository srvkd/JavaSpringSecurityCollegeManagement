
/*
Filename:GeneralServiceImpl.java

Author:Saurabh Kedia

Description: Service Layer implementation of the interfacing calling the DAO classes
 which will then connect to the database and return results 

Date Modified:31.10.2018

*/

package com.college.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.college.core.beans.SecurityQuestion;
import com.college.core.beans.User;
import com.college.dao.impl.GeneralDAOImpl;
import com.college.exception.CollegeException;
import com.college.service.interfaces.GeneralServices;

public class GeneralServiceImpl implements GeneralServices
{

	// Injecting DAO to the service layer 
	@Autowired
	private GeneralDAOImpl genDAO;
	
	
	@Override
	public int changePassword(String username, String password,String oldPassword) throws CollegeException {

		return genDAO.changeThePassword(username, password,oldPassword);
	}
	@Override
	public User fetchUserDetails(String username) throws CollegeException {

		return genDAO.fetchUserDetails(username);
	}
	@Override
	public int updateUser(User user) throws CollegeException {
		return genDAO.updateUser(user);
	}

	@Override
	public int deleteUser(int id) throws CollegeException {
		// TODO Auto-generated method stub
		return genDAO.deleteThisUser(id);
	}
	@Override
	public List<User> viewAllUsers() throws CollegeException {
		return genDAO.viewAllUsers();
	}
	@Override
	public User findThisUser(String username) {
		// TODO Auto-generated method stub
		return genDAO.findThisUser(username);
	}
	@Override
	public SecurityQuestion getSecurityQuestion(String username) throws CollegeException {
		// TODO Auto-generated method stub
		return genDAO.getSecurityQuestion(username);
	}
	@Override
	public boolean verifyQuestion(SecurityQuestion question,String username) {
		// TODO Auto-generated method stub
		return genDAO.verifyQuestion(question,username);
	}
	@Override
	public List<User> searchUserByName(String name) throws SQLException,CollegeException
	{
		// TODO Auto-generated method stub
		return genDAO.searchUserByName(name);
	}
	@Override
	public int resetThePassword(String username, String password)
			throws CollegeException {
		// TODO Auto-generated method stub
		return genDAO.resetThePassword(username, password);
	}

}
