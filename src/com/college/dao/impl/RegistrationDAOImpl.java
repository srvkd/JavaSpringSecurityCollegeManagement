package com.college.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.jboss.logging.Logger;

import com.college.controller.MainController;
import com.college.core.beans.User;
import com.college.dao.interfaces.RegistrationDAO;
import com.college.datasource.DBUtil;
import com.college.exception.CollegeException;

public class RegistrationDAOImpl implements RegistrationDAO
{
	private final static Logger logger=Logger.getLogger(MainController.class);

	private Connection con=null;
	public int registerTheUser(User user) throws SQLException, CollegeException 
	{
		PreparedStatement registerUser=null;
		int executeRegister=0;
		con=DBUtil.getConnection();

		try {
			registerUser=con.prepareStatement("insert into user values (?,?,?,?,?,?,?,?,?,?,?)");  
			registerUser.setInt(1,user.getUser_id());
			registerUser.setString(2,user.getFirstname());
			registerUser.setString(3,user.getLastname());
			registerUser.setString(4,"ROLE_USER");
			registerUser.setString(5,user.getAddress());
			registerUser.setString(6,user.getContact());
			registerUser.setString(7,user.getPassword());
			registerUser.setString(8,user.getEmail());
			registerUser.setString(9,"true");
			registerUser.setString(10,java.time.LocalDate.now().toString());
			
			registerUser.setString(11,user.getUsername());
			

			if(registerUser.executeUpdate()==1)
			{
				logger.info("Initial data saved in user table for "+ user.getUsername());
				int userId=fetchUserId(user.getUsername());
				logger.info("fetched user id for user "+user.getUsername());
				if(userId!=0)
				{
					user.setUser_id(userId);
					if(saveSecurityQuestion(user)==1)
					{
						logger.info("Saved security questions for user " +user.getUsername());
						executeRegister++;

					 
					}
					else
					{
						logger.error("Could not save security questions for user " +user.getUsername());
						
						executeRegister=0;
						con.rollback();
					}

				}
				else
				{
					logger.error("Could not fetch user id for user "+user.getUsername());
					
					con.rollback();
				}
			}


			return executeRegister;
		}
		
	
		catch (SQLIntegrityConstraintViolationException e) 
		{
			if(e.getMessage().endsWith("'email'"))
			{
				logger.error(e);
							throw new CollegeException("Email Id already exists please use a new email id");
			}
			if(e.getMessage().endsWith("'username'"))
			{
				logger.error(e);
				throw new CollegeException("Sorry,Username already taken, please use a new username");
			}
			if(e.getMessage().endsWith("'contact'"))
			{
				logger.error(e);
				throw new CollegeException("Sorry,Contact number already exists, please use a new username");
			}
			logger.error(e);
						
			throw new CollegeException("Unexpected error");

		}
		
		catch(Exception e)
		{

			logger.error(e);
			throw new CollegeException("Unexpected Error occured");

		}
		
		finally
		{
			try 
			{
				
				if(registerUser!=null)
					{
					registerUser.close();
					}
				if(con!=null)
					{
					con.close();
					}
			}
						catch(Exception e)
			{

				logger.error(e);
			}
		}
	}

	public int saveSecurityQuestion(User question) throws SQLException, CollegeException
	{
		int saveSecurityQuestion = 0;
		con=DBUtil.getConnection();
		PreparedStatement saveSecQue1Query=null;
		try {
			saveSecQue1Query=con.prepareStatement("insert into security_answer values (?,?,?,?,?,?)");  
			saveSecQue1Query.setInt(1,question.getQuestion().getId());
			saveSecQue1Query.setString(2,question.getQuestion().getQuestion1());

			saveSecQue1Query.setString(3,question.getQuestion().getAnswer1());
			saveSecQue1Query.setInt(4,question.getUser_id());
			saveSecQue1Query.setString(5,question.getQuestion().getQuestion2());
			saveSecQue1Query.setString(6,question.getQuestion().getAnswer2());
			
			saveSecurityQuestion=saveSecQue1Query.executeUpdate();
			if(saveSecurityQuestion==1)
			{
				logger.info("saved security questions for user "+ question.getUser_id());
				return saveSecurityQuestion;
				
			}
			else
			{
				logger.error("Error saving security questions for user "+ question.getUser_id());
				
				return 0;
			}

					}
		catch (SQLException e) 
		{
			logger.error(e);
			logger.error(e.getMessage()+" for userID "+question.getUser_id());
			throw new CollegeException("Something went wrong while adding your security questions.");
			
		}
		finally
		{
			try 
			{
				if(saveSecQue1Query!=null)
				{
					saveSecQue1Query.close();
				}
				if(con!=null)
					{
					con.close();
					}
			
			}
			catch (Exception e)
			{
			}
		}	

	}



	public int fetchUserId(String user)
	{
		con=DBUtil.getConnection();
		int userId=0;
		PreparedStatement fetchUserId=null;
		ResultSet fetchedUserID=null;
		String selectQuery="select user_id from user where username=?";
		try {

			fetchUserId = con.prepareStatement(selectQuery);

			fetchUserId.setString(1,user);
			fetchedUserID=fetchUserId.executeQuery();
			while(fetchedUserID.next())
			{
				userId=fetchedUserID.getInt(1);
				logger.info("fetched user id for "+user);
			}
			return userId;
		} catch (SQLException e) {
			logger.error(e);
			return 0;
		}
		catch (Exception e )
		{
			logger.error(e);
			return 0;
		}
		
		finally
		{
			try {
				if(fetchedUserID!=null)
					{
					fetchedUserID.close();
					}
				if(fetchUserId!=null)
					{
					fetchUserId.close();
					}
				if(con!=null)
					{
					con.close();
					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e);
					
			}
		}
		
		
	}




}

