/*
FileName:User.java

Author:Saurabh Kedia

Description: Holds the tangible users data. Server side validaton is performed by annotating the member variables 
with a corresponding error message if the validation fails.


Date Modified:31.10.2018

 */

package com.college.core.beans;

import java.sql.Date;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class User {

	private int user_id;
	@Size(min=2)
	private String firstname;
	@Size(min=2)
	private String lastname;
	@Size(min=4,max=5)
	private String usertype;
	@Size(min=2)
	private String address;
	@Pattern(message="Only 9 digit numbers are allowed", regexp = "[0-9]{9}")
	private String contact;
	@Pattern(message="Password must contain atleast 1"
			+ "number,lowercase,uppercase,special character with no white spaces and should be atleast 8 characters"
			,regexp="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}")
	private String password;
	private String account_status;
	@Pattern(message="Email format is not valid",regexp="^[A-Za-z0-9+_.-]+@(.+)$")
	private String email;
	private Date password_last_updated;

	@Size(min=2,max=7)
	private String username;

	@Valid
	public SecurityQuestion question;


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccount_status() {
		return account_status;
	}
	public void setAccount_status(String account_status) {
		this.account_status = account_status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getPassword_last_updated() {
		return password_last_updated;
	}
	public void setPassword_last_updated(Date password_last_updated) {
		this.password_last_updated = password_last_updated;
	}
	public SecurityQuestion getQuestion() {
		return question;
	}
	public void setQuestion(SecurityQuestion question) {
		this.question = question;
	}



}
