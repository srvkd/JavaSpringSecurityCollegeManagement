package com.college.salt;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class SaltGenerator {
	public static String generateSalt()
	{
		String salt=BCrypt.gensalt(12);
		return salt;
	}
	
	public static String generateHash(String password,String salt)
	{
		String hashedPassword=BCrypt.hashpw(password, salt);
		return hashedPassword;
	}
	
}
