/*
FileName:SecurityQuestion.java

Author:Saurabh Kedia

Description: Holds the Security questions of a user and returns them 

Date Modified:31.10.2018

*/

package com.college.core.beans;

import javax.validation.constraints.Pattern;

public class SecurityQuestion 
{
	
	private int id;
	@Pattern(regexp="^[a-zA-Z0-9_ ]*$",message="Use only plain letters, not even question marks")
	private String question1;
	@Pattern(regexp="^[a-zA-Z0-9_ ]*$",message="Use only plain letters, not even question marks")
	private String question2;

	@Pattern(regexp="^[a-zA-Z0-9_ ]*$",message="Use only plain letters, not even question marks")
	private String answer1;

	@Pattern(regexp="^[a-zA-Z0-9_ ]*$",message="Use only plain letters, not even question marks")
	private String answer2;
	
	private User userId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}
	
	
	
	
	
}
