
/*
Filename:Mailer.java

Author:Saurabh Kedia

Description: Class for generating a 6 digit OTP and send the mail to the user for account recovery.

Date Modified:31.10.2018

*/

package com.college.mailer;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Mailer 

{
	
	
	// store generated random number in a string 
	private String otpHolder=generateNumber();
	
	
	
	
	public String getOtpHolder() {
		return otpHolder;
	}

	public void setOtpHolder(String otpHolder) {
		this.otpHolder = otpHolder;
	}

	//generate 6 digit random number 
	public  String generateNumber()
	{
		Random rnd = new Random();
	    int number = rnd.nextInt(999999);

	    return String.format("%06d", number);
	}

	
	//send email to the user via SMTP having the OTP in the mail body 
	public static void sendmail(String email,String otp)
	{

		final String username = "saurabhkedia88@gmail.com";
		final String password = "";
		final String to=email;
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", username);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, to);


			message.setSubject("College Project - Account Recovery");
			message.setText(otp);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, username,password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		catch (MessagingException me) {
			me.printStackTrace();
		}
	}
}

