/*
FileName:CollegeException.java

Author:Saurabh Kedia

Description: User defined exception for throwing exceptions to the user for security purpose. 

Date Modified:31.10.2018

*/

package com.college.exception;

public class CollegeException extends Exception
{
	// takes a message as a paramter which can be thrown to the user 
	private String message;
	public CollegeException(String message)
	{
		this.message=message;
	}
	
	public String toString()
	{
		return message;
	}
}
