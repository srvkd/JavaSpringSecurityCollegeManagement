/*
FileName:DBUtil.java

Author:Saurabh Kedia

Description: Datasource connection definition for connecting to database. 

Date Modified:31.10.2018

*/



package com.college.datasource;

import java.sql.Connection;
import java.sql.DriverManager;



public class DBUtil 
{
	// returns JDBC connection object by connecting to database 
	public static Connection getConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","");
			return con;
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			return null;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;

		}


	}
}
