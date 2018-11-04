package com.college.core.beans;

import javax.validation.constraints.Pattern;

public class PasswordChangeBean 
{
	@Pattern(message="Password must contain atleast 1"
			+ "number,lowercase,uppercase,special character with no white spaces and should be atleast 8 characters"
			,regexp="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}")
	
	private String oldPassword;
	@Pattern(message="Password must contain atleast 1"
			+ "number,lowercase,uppercase,special character with no white spaces and should be atleast 8 characters"
			,regexp="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}")
	private String newPassword;
	@Pattern(message="Password must contain atleast 1"
			+ "number,lowercase,uppercase,special character with no white spaces and should be atleast 8 characters"
			,regexp="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}")
	private String confirmPassword;
	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}
	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	
}
