package com.college.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import com.college.controller.MainController;
import com.college.core.beans.SecurityQuestion;
import com.college.core.beans.User;
import com.college.dao.interfaces.GeneralDAO;
import com.college.datasource.DBUtil;
import com.college.exception.CollegeException;

public class GeneralDAOImpl implements GeneralDAO {

	private static Logger logger=Logger.getLogger(MainController.class);

	private Connection con=null;
	
	
	@Override
	public int resetThePassword(String username, String password) throws CollegeException  
	{
		PreparedStatement changeThePassword=null;
		int counter=0;
		try {
			con=DBUtil.getConnection();
			changeThePassword=con.prepareStatement("update user set password=? where username=?");
			changeThePassword.setString(1,password);
			changeThePassword.setString(2,username);

			counter =changeThePassword.executeUpdate();

			if(counter==1)
			{
				logger.info("Password changed for user "+username);

			}
			else
			{
				logger.warn("Password update failed for user "+username);

			}

			return counter;
		} catch (SQLException e) 
		{
			logger.error(""+e.getMessage());
			throw new CollegeException("Password could not be updated");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			throw new CollegeException("Unexpected error occured");

		}
		finally
		{
			try {

				if(changeThePassword!=null)
				{
					changeThePassword.close();

				}
				if(con!=null)
				{

					con.close();	
				}
			} catch (Exception e) 
			{
				logger.error(e);

			}
		}


	}

	@Override
	public int changeThePassword(String username, String password,String oldPassword) throws CollegeException  
	{
		PreparedStatement changeThePassword=null;
		int counter=0;
		try {
			con=DBUtil.getConnection();
			changeThePassword=con.prepareStatement("update user set password=? where (username=? AND password=?)");
			changeThePassword.setString(1,password);
			changeThePassword.setString(2,username);
			changeThePassword.setString(3,oldPassword);

			counter =changeThePassword.executeUpdate();

			if(counter==1)
			{
				logger.info("Password changed for user "+username);

			}
			else if(counter==0)
			{
				logger.warn("New password was same as old password "+username);

			}

			return counter;
		} catch (SQLException e) 
		{
			logger.error(""+e.getMessage());
			throw new CollegeException("Password could not be updated");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			throw new CollegeException("Unexpected error occured");

		}
		finally
		{
			try {

				if(changeThePassword!=null)
				{
					changeThePassword.close();

				}
				if(con!=null)
				{

					con.close();	
				}
			} catch (Exception e) 
			{
				logger.error(e);

			}
		}


	}

	@Override
	public User fetchUserDetails(String username) throws CollegeException {
		int userId=fetchUserId(username);
		User fetchedUser=new User();
		SecurityQuestion question=new SecurityQuestion();
		
		PreparedStatement fetchUserDetails=null;
		ResultSet rs=null;
		try {
			con=DBUtil.getConnection();
			fetchUserDetails=
					con.prepareStatement("select u.*,sa.question,sa.answer,sa.question2,sa.answer2 from user u,security_answer sa where u.user_id=?");

			fetchUserDetails.setInt(1,userId);

			rs=fetchUserDetails.executeQuery();
			while(rs.next())
			{
				fetchedUser.setFirstname(rs.getString("firstname"));
				fetchedUser.setLastname(rs.getString("lastname"));
				fetchedUser.setAddress(rs.getString("Address"));
				fetchedUser.setContact(rs.getString("contact"));
				fetchedUser.setEmail(rs.getString("email"));
				question.setAnswer1(rs.getString("answer"));
				question.setAnswer2(rs.getString("answer2"));
				question.setQuestion1(rs.getString("question"));
				question.setQuestion2(rs.getString("question2"));
				fetchedUser.setQuestion(question);
			}
			logger.info("User details fetched for "+username);
			return fetchedUser;


		} catch (SQLException e) {

			logger.error(e);
			return null;
		}
		catch(Exception e)
		{
			logger.error(e);
			throw new CollegeException("Problem fetching the users");
		}

		finally
		{
			try {
				if(rs!=null)
				{
					rs.close();

				}
				if(fetchUserDetails!=null)
				{
					fetchUserDetails.close();

				}
				if(con!=null){
					con.close();

				}
			}
			catch (Exception e) 
			{
				logger.error(e);
			}
		}



	}

	public int fetchUserId(String user)
	{
		PreparedStatement fetchUserId=null;
		ResultSet fetchedUserID=null;
		con=DBUtil.getConnection();
		int userId=0;
		String selectQuery="select user_id from user where username=?";
		try {
			fetchUserId = con.prepareStatement(selectQuery);

			fetchUserId.setString(1,user);
			fetchedUserID=fetchUserId.executeQuery();
			while(fetchedUserID.next())
			{

				userId=fetchedUserID.getInt(1);

			}
			logger.info("user id "+userId+" fetched for "+user);
			return userId;
		} catch (SQLException e) {

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

	@Override
	public int updateUser(User user) throws CollegeException {
		PreparedStatement updateUserTable=null;

		int counter=0;
		int fetchUserId=fetchUserId(user.getUsername());
		try {

			con=DBUtil.getConnection();
			updateUserTable=con.prepareStatement("update user set firstname=?,lastname=?,email=?,address=?,contact=? where username=?");


			updateUserTable.setString(1,user.getFirstname());
			updateUserTable.setString(2,user.getLastname());
			updateUserTable.setString(3,user.getEmail());
			updateUserTable.setString(4,user.getAddress());

			updateUserTable.setString(5,user.getContact());
			updateUserTable.setString(6,user.getUsername());


			counter =updateUserTable.executeUpdate();

			if(counter==1)
			{
				logger.info("Initial Details updated in table user for user "+ user.getUsername());
				PreparedStatement securityTable=con.prepareStatement("update security_answer set question=?,answer=?,question2=?,answer2=? where user_id=?");


				securityTable.setString(1,user.getQuestion().getQuestion1());
				securityTable.setString(2,user.getQuestion().getAnswer1());
				securityTable.setString(3,user.getQuestion().getQuestion2());
				securityTable.setString(4,user.getQuestion().getAnswer2());

				securityTable.setInt(5,fetchUserId);

				if(securityTable.executeUpdate()==1)
				{
					logger.info("Details updated in security answer table for user "+ user.getUsername());
					counter++;
				}
				else
				{
					logger.error("Details not updated in security answer table for user "+ user.getUsername());

					con.rollback();
					throw new CollegeException("Unexpected Error");
				}

			}
			else
			{
				logger.error("Update in table user failed for user"+user.getUsername());
				con.rollback();
			}
			return counter;
		} catch (SQLIntegrityConstraintViolationException e) 
		{

			logger.error(e);
			if(e.getMessage().endsWith("'email'"))
			{
				throw new CollegeException("Email Id already exists please use a new email id");
			}
			if(e.getMessage().endsWith("'username'"))
			{
				throw new CollegeException("Sorry,Username already taken, please use a new username");
			}
			if(e.getMessage().endsWith("'contact'"))
			{
				throw new CollegeException("Sorry,Contact number already exists, please use a new username");
			}

			return 0;			


		}catch (SQLException e) 
		{

			logger.error(e);

			throw new CollegeException("Unexpected error occured");
		}
		catch(Exception e)
		{
			logger.error(e);

			throw new CollegeException("Unexpected error occured");


		}

		finally
		{
			try {
				if(updateUserTable!=null)
				{

					updateUserTable.close();
				}

				if(con!=null)
				{
					con.close();

				}

			} catch (Exception e) {
				logger.error(e);
			}

		}


	}



	@Override
	public List<User> viewAllUsers() throws CollegeException 
	{
		PreparedStatement fetchAllUsers=null;
		ResultSet rs=null;
		List<User> users=new ArrayList<User>();
		con=DBUtil.getConnection();
		try {
			fetchAllUsers=con.prepareStatement("select * from user");
			rs=fetchAllUsers.executeQuery();
			while(rs.next())
			{
				User user=new User();

				user.setUser_id(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setContact(rs.getString("contact"));
				users.add(user);
			}

			return users;
		}
		catch(Exception e)
		{
			logger.error(e);
			throw new CollegeException("Error fetching all the users ");

		}

		finally
		{
			try {
				if(rs!=null)
				{
					rs.close();
						
				}
				if(fetchAllUsers!=null)
				{
					fetchAllUsers.close();
							
				}
				
				if(con!=null)
				{

						
				}con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
		}
	}
	@Override
	public int deleteThisUser(int id) throws CollegeException 
	{
		PreparedStatement deleteUser=null;
		con=DBUtil.getConnection();
		try {
			deleteUser=con.prepareStatement("delete from user where user_id=?");
			deleteUser.setInt(1,id);
			if(deleteUser.executeUpdate()==1)
			{
				logger.info("User "+ id +"deleted");
				return 1;
			}
			else
			{
				logger.error("User "+ id +" not found");

				return 0;
			}

		}

		catch(Exception e)
		{
			throw new CollegeException("User could not be deleted");
		}

		finally
		{
			try {
				if(deleteUser!=null)
				{
					deleteUser.close();
						
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

	@Override
	public User findThisUser(String username) {
		con=DBUtil.getConnection();
		PreparedStatement fetchThisUser=null;
		ResultSet rs=null;
		try {
			SecurityQuestion question=new SecurityQuestion();

			User user=new User();
			fetchThisUser=con.prepareStatement("select username,email,question,question2 from user,security_answer "
					+ "where user.user_id=security_answer.user_id "
					+ "and username=?");
			fetchThisUser.setString(1,username);
			rs=fetchThisUser.executeQuery();
			while(rs.next())
			{
				user.setEmail(rs.getString("email"));
				question.setQuestion1(rs.getString("question"));
				question.setQuestion2(rs.getString("question2"));
				user.setQuestion(question);	

			}

			logger.info("User found for "+ username);

			return user;

		}

		catch(Exception e)
		{
			logger.error(e);
			return null;
		}
		finally
		{
			try {
				if(fetchThisUser!=null)
				{
					fetchThisUser.close();
				}
				if(con!=null)
					{
					con.close();
					}
			} catch (Exception e) 
			{
				logger.error(e);
			}
		}


	}


	@Override
	public SecurityQuestion getSecurityQuestion(String username) throws CollegeException 
	{
		ResultSet rs=null;
		SecurityQuestion questions=new SecurityQuestion();
		PreparedStatement fetchSecurityQuestion=null;
		int userId=fetchUserId(username);
		con=DBUtil.getConnection();
		try {

			fetchSecurityQuestion=
					con.prepareStatement("select question,answer,question2,answer2 from security_answer where user_id=?");
			fetchSecurityQuestion.setInt(1,userId);
			rs=fetchSecurityQuestion.executeQuery();
			while(rs.next())
			{
				questions.setQuestion1(rs.getString("question"));
				questions.setQuestion2(rs.getString("question2"));
			}

			logger.info("Security questions fetched for user "+username);
			return questions;
		} catch (SQLException e) {

			logger.error(e);
			throw new CollegeException("Unexpected Error");
		}
		finally
		{
			try {
				if(rs!=null)
					{
					rs.close();
					}
				if(fetchSecurityQuestion!=null)
					{
					fetchSecurityQuestion.close();
					}
				if(con!=null)
					{
					con.close();
					}
			} catch (Exception e) 
			{
				logger.error(e);
			}
		}
	}

	@Override
	public boolean verifyQuestion(SecurityQuestion question,String username) 
	{
		PreparedStatement verifyQuestion=null;
		ResultSet rs=null;
		String answer=null;
		String answer2=null;
		int fetchUserId=fetchUserId(username);
		con=DBUtil.getConnection();
		try {

			verifyQuestion=
					con.prepareStatement
					("select u.username,sa.question,sa.answer,sa.question2,sa.answer2 from user u,security_answer sa "
							+ "where u.user_id=sa.user_id "
							+ "	AND u.user_id=? "
							);
			verifyQuestion.setInt(1,fetchUserId);

			rs=verifyQuestion.executeQuery();

			while(rs.next())
			{
				answer=rs.getString("answer");
				answer2=rs.getString("answer2");



			}

				if(answer.equals(question.getAnswer1()) && answer2.equals(question.getAnswer2()))
				{
					logger.info("Security Questions verified for user "+ username);
					return true;
				}
				else 
				{

					logger.info("Security Questions verification failed for user "+ username);

					return false;
				}

			
			
		}
		catch(Exception e)
		{
			return false;
		}
		finally
		{
			try {
				
				if(rs!=null)
					{
					rs.close();
					}
				if(verifyQuestion!=null)
					{
					verifyQuestion.close();
					}
				if(con!=null)
					{
					con.close();
					}
			} catch (Exception e) 
			{

				logger.error(e);
			}

		}
	}

	@Override
	public List<User> searchUserByName(String name) throws CollegeException
	{
		List<User> userList=new ArrayList<User>();
		con=DBUtil.getConnection();

		PreparedStatement searchUserByName=null;
		ResultSet rs=null;
		try {
			searchUserByName = con.prepareStatement("select firstname,lastname,email,contact from user where firstname LIKE ?");
			searchUserByName.setString(1,"%"+name+"%");
			rs=searchUserByName.executeQuery();
			while(rs.next())
			{
				User user=new User();
				user.setFirstname(rs.getString(1));
				user.setLastname(rs.getString(2));
				user.setEmail(rs.getString(3));
				user.setContact(rs.getString(4));
				userList.add(user);

			}
			
			if(userList.isEmpty())
			{
				throw new CollegeException("No Users founrd");
			}
			

		} catch (SQLException e) 
		{
			logger.error("Could not find user by name "+name);

		}
		catch(Exception e)
		{
			logger.error(e);
		}

		finally
		{
			try {

				if(rs!=null)
					{
					rs.close();
					}
				if(searchUserByName!=null)
					{
					searchUserByName.close();
					}
				if(con!=null)
					{
					con.close();
					}

			} catch (SQLException e) {
				logger.error(e);
			}
		}


		return userList;
	}
}