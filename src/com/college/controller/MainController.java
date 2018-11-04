/*
FileName:MainController.java

Author:Saurabh Kedia

Description: This is the controller of the Spring MVC. It is responsible for accepting requests from the client , processing the request
and rendering the desired view to the end user.

Date Modified:31.10.2018

 */
package com.college.controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.college.core.beans.PasswordChangeBean;
import com.college.core.beans.SecurityQuestion;
import com.college.core.beans.User;
import com.college.exception.CollegeException;
import com.college.mailer.Mailer;
import com.college.service.impl.GeneralServiceImpl;
import com.college.service.impl.RegistrationServiceImpl;

@Controller
public class MainController {

	// Service layer object for registration module autowired from spring
	// factory
	@Autowired
	private RegistrationServiceImpl service;

	// Service layer object for general module autowired from spring factory

	@Autowired
	private GeneralServiceImpl genService;

	// BcryptPassword encoder for password hashing with salt

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	// JBoss logger for MainController.java
	private static final Logger logger = Logger.getLogger(MainController.class);

	private static final String registerPage = "register";

	// Handling Request for '/' in the URL

	@RequestMapping(value = "/", method = RequestMethod.GET)
	// Showing registration page to the user with User bean injected in the
	// spring UI form
	public ModelAndView redirectToRegister(User user) {
		System.out.println("rendering index page");
		logger.info("Rendering register page to the user ");
		ModelAndView model = new ModelAndView();
		model.setViewName(registerPage);

		return model;
	}

	// Handling Request for '/register' in the URL

	// Showing registration page to the user with User bean injected in the
	// spring UI form

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPage(User user) {

		logger.info("rendering register page to the user ");
		return registerPage;

	}

	// Handles post request from the registration page and saves data in the
	// database
	@RequestMapping(value = "/registerTheUser", method = RequestMethod.POST)
	public ModelAndView registerTheUser(
			@ModelAttribute("user") @Valid User user, BindingResult result,
			ModelAndView model) throws SQLException {

		System.out.println(user.getQuestion().getQuestion1());
		System.out.println(user.getQuestion().getAnswer1());
		System.out.println(user.getQuestion().getQuestion2());
		System.out.println(user.getQuestion().getAnswer2());

		if (result.hasErrors()) // check if the UI validation had errors
		{

			logger.warn("User " + user.getUsername()
					+ " tried registering but failed with errors");
			return new ModelAndView(registerPage);
		} else {
			logger.info("User " + user.getUsername()
					+ " filled the form with no errors");
			String hashedPassword = bCryptPasswordEncoder.encode(user
					.getPassword()); // password hashing with auto generated
										// salt
			user.setPassword(hashedPassword);
			try {
				if (service.registerTheUser(user) > 0) // check if rows greatre
														// than 0 were affected
														// in the database
				{

					logger.info("User " + user.getUsername()
							+ " registered into database");
					model.setViewName(registerPage);
					model.addObject("success", "User registered");
					model.addObject("user", new User());
					return model;
				} else {

					logger.error("Error occurred when user "
							+ user.getUsername()
							+ " tried to register properly");
					return new ModelAndView(registerPage);
				}
			} catch (CollegeException e) {
				logger.error("During registration for user "
						+ user.getUsername() + e.toString());
				model.addObject("error", "Error: " + e.toString());
				model.setViewName(registerPage);
				logger.error(e);
				return model;

			} catch (Exception e) {

				logger.error("During registration for user "
						+ user.getUsername() + e.toString());
				model.addObject("error", "Error: occurred");
				model.setViewName(registerPage);
				logger.error(e);
				return model;
			}

		}

	}

	// Redirect user to the login form

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginForm() {
		logger.info("Rendering login page");
		return "login";
	}

	// Redirect user to the homePage

	@RequestMapping(value = "/homePage", method = RequestMethod.GET)
	public ModelAndView userHomepage() {
		logger.info("Rendering homepage");

		ModelAndView model = new ModelAndView();
		model.setViewName("homePage");
		model.addObject("username", getPrincipal());
		return model;
	}

	// Render change password page

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ModelAndView changePassword() {
		logger.info("Rendering change password page");
		ModelAndView model = new ModelAndView();
		model.addObject("password", new PasswordChangeBean());
		model.setViewName("changePassword");
		return model;
	}

	// POST request handler for changing password

	@RequestMapping(value = "/changeThePassword", method = RequestMethod.POST)
	public ModelAndView changePassword(
			@ModelAttribute("password") @Valid PasswordChangeBean passwords,
			BindingResult result, ModelAndView model) {
		logger.info("User " + getPrincipal() + " tried chaning the password");

		if (result.hasErrors()) // check if the UI validation had errors
		{

			logger.warn("User " + getPrincipal()
					+ "'s form validation failed for chaning password");
			return new ModelAndView("changePassword");
		} else {
			logger.info("User " + getPrincipal()
					+ "'s form validation succeeded for chaning password");
			if (passwords.getNewPassword().equals(
					passwords.getConfirmPassword())) {
				logger.info("User " + getPrincipal()
						+ "'s new password and confirm password matched");
				String oldPass = bCryptPasswordEncoder.encode(passwords
						.getOldPassword());
				String pass = bCryptPasswordEncoder.encode(passwords
						.getNewPassword()); // hashing the new password with
											// salt
				try {
					int counter = genService.changePassword(getPrincipal(),
							pass, oldPass);
					if (counter == 0) // check if 1 row was updated in the
										// database
					{
						logger.warn("Old password and new password were same for user "
								+ getPrincipal());
						model.addObject("error",
								"New password and old Password must be different.");
						model.setViewName("changePassword");
						return model;
					} else if (counter == 1) {
						logger.info("Password successfully changed for user "
								+ getPrincipal());
						model.addObject("error",
								"New password and old Password must be different.");
						model.setViewName("changePassword");
						return model;
					}

				} catch (CollegeException e) {
					logger.error("Password change failed due to "
							+ e.getMessage());
					model.addObject("error", e.toString());
					model.setViewName("changePassword");
					return model;
				} catch (Exception e) {
					logger.error(e);
					model.addObject("error", e.toString());
					model.setViewName("changePassword");
					return model;
				}

			} else {
				logger.warn("User " + getPrincipal()
						+ "'s passwords did not match");
				model.addObject("error", "Passwords Do not match.");
				model.setViewName("changePassword");
				return model;
			}
			return model;

		}

	}

	// rendering update details page having the logged in user's details
	@RequestMapping(value = "/updateDetails", method = RequestMethod.GET)
	public ModelAndView fetchUpdatePage() {
		ModelAndView model = new ModelAndView();

		User fetchedUser;
		try {
			System.out.println("entered try");
			fetchedUser = genService.fetchUserDetails(getPrincipal());

			System.out.println("user details" + fetchedUser);
			// fetch logged in users details
			if (fetchedUser != null) {
				System.out.println(fetchedUser.getUsertype());
				logger.info("Rendering update details page to user "
						+ fetchedUser.getUsername());
				model.addObject("user", fetchedUser);
				model.setViewName("updateDetails");
				return model;
			} else {

				System.out.println("reached else ");
				logger.error("Could not fetch details for user "
						+ getPrincipal());
				model.addObject("message", "Could not fetch your details");
				return model;
			}

		} catch (CollegeException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			return model;
		} catch (Exception e) {
			logger.error(e);
			return model;
		}
	}

	// render account recovery page

	@RequestMapping(value = "/accountRecovery", method = RequestMethod.GET)
	public String accountRecoveryPage() {
		logger.info("Rendering account Recovery Page");
		return "accountRecovery";
	}

	// search the user's email id for sending the OTP on the users email for
	// starting the password recovery process

	@RequestMapping(value = "/findUser", method = RequestMethod.POST)
	public ModelAndView findThisUser(@RequestParam("username") String username,
			HttpServletRequest request) {
		logger.info(username + " tried to recovery account");
		User user = new User();
		user = genService.findThisUser(username); // find email by its username
		if (user != null) {
			logger.info("Found email ID for user " + username);
			Mailer mailer = new Mailer();
			ModelAndView model = new ModelAndView();
			model.setViewName("/verifyOTP");
			String otp = mailer.getOtpHolder(); // generate 6 digit OTP
			Mailer.sendmail(user.getEmail(), otp); // send generated OTP to the
													// users email

			logger.info("Mail sent to the user " + username + " having OTP");
			HttpSession session = request.getSession(); // setting OTP to the
														// HttpSession
			session.setAttribute("otp", otp);
			session.setAttribute("username", username); // setting username to
														// HttpSession
			logger.info("Set otp and username in the HttpSession");
			logger.info("Redirecting user to verifyOTP page");

			return model;

		} else {

			logger.warn("Could not find email ID for user " + username
					+ ".Rendering account recovery page back to the user");

			return new ModelAndView("accountRecovery");
		}

	}

	// POST handler to verify OTP in password recovery process

	@RequestMapping(value = "/verifyTheOTP", method = RequestMethod.POST)
	public ModelAndView verifyTheOTP(@RequestParam("otp") String otp,
			HttpServletRequest request, HttpSession session) {
		ModelAndView model = new ModelAndView();
		if (otp.equals(session.getAttribute("otp"))) // / check if the OTP in
														// the session is equal
														// to user input OTP
		{
			logger.info("User " + session.getAttribute("username").toString()
					+ " successfully verified the OTP");

			SecurityQuestion question;
			try {
				question = genService.getSecurityQuestion(session.getAttribute(
						"username").toString()); // fetch security questions for
													// user
				model.addObject("question", question);
				model.setViewName("securityQuestion");

				model.addObject("questionBean", new SecurityQuestion());
				logger.info("Fetched security questions for the user");

				return model;
			} catch (CollegeException e) {
				logger.error(e);
				return model;

			} catch (Exception e) {
				logger.error(e);
				return model;
			}

		} else {

			logger.warn(session.getAttribute("username").toString()
					+ " entered wrong OTP");
			logger.warn("destroyed sessions for the user "
					+ session.getAttribute("username").toString());

			session.invalidate(); // destroy session object which had OTP and
									// username
			model.addObject("error", "OTP did not match. Please try again");
			model.setViewName("accountRecovery");
			logger.info("rendering back account recovery page");
			return model;
		}

	}

	// POST handler for verify the security answers put by user with the answer
	// in the database

	@RequestMapping(value = "/verifySecurityQuestions", method = RequestMethod.POST)
	public ModelAndView verifyTheOTP(
			@ModelAttribute("questionBean") SecurityQuestion question,
			HttpServletRequest request, HttpSession session) {
		ModelAndView model = new ModelAndView();
		if (genService.verifyQuestion(question, session
				.getAttribute("username").toString())) // verify questions
		{

			logger.info(session.getAttribute("username").toString()
					+ " verified the security question");
			model.addObject("user", new User());
			model.setViewName("resetUserPassword");

		}

		else {
			// if the questions were answered wrongly, destroy session
			// attributes for that user
			logger.info(session.getAttribute("username").toString()
					+ " did not verify the security question correctly");
			logger.info("destroyed session");
			model.addObject("error",
					"Your answers did not match from the database");
			model.setViewName("accountRecovery");

			session.invalidate();
		}

		return model;

	}

	// POST handler for reseting user password

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ModelAndView resetPassword(@ModelAttribute("user") @Valid User user,
			BindingResult result,
			@RequestParam("confirmPass") String confirmPass,
			HttpServletRequest request, HttpSession session, ModelAndView model) {

		if (!result.hasErrors()) // check if user entered password according to
									// pattern
		{

			if (user.getPassword().equals(confirmPass)) {
				logger.info("New password matched with confirm password for user "
						+ session.getAttribute("username").toString());
				String newPass = bCryptPasswordEncoder.encode(user
						.getPassword());
				try {
					int counter = genService.resetThePassword(session
							.getAttribute("username").toString(), newPass);
					if (counter == 1 || counter == 0) // check if 1 row was
														// updated
					{
						logger.info("Password changed successfully for user "
								+ session.getAttribute("username").toString());
						model.addObject("success",
								"Password was changed successfully. Please login");
						session.invalidate();
						model.setViewName("register");
					} else {
						// destroy session if the password was not updated
						logger.warn("Password updated failed for user "
								+ session.getAttribute("username").toString());
						model.setViewName("/register");
						session.invalidate();
					}
				} catch (CollegeException e) {
					logger.error("Error while changing password for user "
							+ session.getAttribute("username").toString()
							+ e.getMessage());
					model.addObject("error", e.getMessage());
					session.invalidate();
					logger.error(e);
					return model;
				} catch (Exception e) {
					logger.error("Error while changing password for user "
							+ session.getAttribute("username").toString()
							+ e.getMessage());
					model.addObject("error", e.getMessage());
					session.invalidate();
					logger.error(e);
					return model;
				}
			} else // if new password and confirm password did not match
			{
				logger.warn("Passwords did not match for resetting for user "
						+ session.getAttribute("username").toString());
				model.setViewName("accountRecovery");
				model.addObject("error",
						"Passwords did not match.Please try again");
				return model;
			}

		} else {
			// if the password was not entered as per the pattern defined in the
			// User.java Bean

			logger.warn("Form validation failed for user "
					+ session.getAttribute("username").toString()
					+ "for resetting password");

			return new ModelAndView("resetUserPassword");

		}
		return model;
	}

	// Update the logged in user's details in the database
	@RequestMapping(value = "/updateMyDetails", method = RequestMethod.POST)
	public ModelAndView updateMyDetails(
			@ModelAttribute("user") @Valid User user, BindingResult result,
			ModelAndView model) {

		if (result.hasErrors()) {
			logger.warn("Form validation errors for user " + getPrincipal()
					+ " while updating details");
			return new ModelAndView("updateDetails");
		} else {
			user.setUsername(getPrincipal());
			try {
				int updateUserCounter = genService.updateUser(user);
				if (updateUserCounter == 2) // check if 'user' and 'security
											// answer table both were updated'
				{
					logger.info("Updated details and security questions for user "
							+ user.getUsername());
					model.addObject("success", "Your details have been updated");
					model.setViewName("updateDetails");
					return model;
				} else if (updateUserCounter == 1) // if only user table got
													// updated
				{
					logger.warn("Update of only User table succeeded for user "
							+ user.getUsername()
							+ " and security table failed ");
					model.addObject("error", "Details could not be updated.");
					model.setViewName("updateDetails");
					return model;

				}

			} catch (CollegeException e) {
				logger.error("Update of details for user failed due to "
						+ e.getMessage());
				model.setViewName("updateDetails");
				model.addObject("error", e.toString());
				return model;
			} catch (Exception e) {
				logger.error(e);
				logger.error("Update of details for user failed due to "
						+ e.getMessage());
				model.setViewName("updateDetails");
				model.addObject("error", "Something went wrong.");
				return model;
			}

		}
		return model;

	}

	// Render all the users to the admin user having a delete button

	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public ModelAndView renderDeleteUserPage() {
		logger.info("Rendering delete user page to admin " + getPrincipal());

		ModelAndView model = new ModelAndView();
		List<User> users;
		try {
			users = genService.viewAllUsers(); // fetch all users from the
												// database

			model.addObject("users", users);
			model.setViewName("/deleteUser");
			return model;
		} catch (CollegeException e) {
			model.addObject("error", "Could not fetch users");
			return model;
		} catch (Exception e) {
			logger.error(e);
			return model;
		}

	}

	// delete the user based on the userID

	@RequestMapping(value = "/deleteThisUser", method = RequestMethod.POST)
	public ModelAndView deleteThisUser(@RequestParam("id") int id)
			throws CollegeException {
		ModelAndView model = new ModelAndView();

		try {
			if (genService.deleteUser(id) == 1) // check if a row was deleted
												// from the database
			{
				logger.info("User " + getPrincipal() + " deleted user id: "
						+ id);
				model.addObject("success", "User deleted");
				List<User> users = genService.viewAllUsers(); // again render
																// all the users
																// to the admin
																// user
				model.addObject("users", users);
				model.setViewName("/deleteUser");
			}

		} catch (CollegeException e) {
			logger.error("Deletion of user failed" + e.getMessage());
			model.addObject("error", e.toString());
			List<User> users = genService.viewAllUsers();
			model.addObject("users", users);
			model.setViewName("/deleteUser");
			return model;
		} catch (Exception e) {
			logger.error("Deletion of user failed" + e.getMessage());
			model.addObject("error", "Deletion of user failed");
			List<User> users = genService.viewAllUsers();
			model.addObject("users", users);
			model.setViewName("/deleteUser");
			return model;
		}

		return model;

	}

	// render a page having a search box for taking input from the user to
	// search for a user by his/her first name

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchUser()

	{
		logger.info("Rendered search user page");
		return "searchUser";
	}

	// POST request handler for fetching all the users by the firstname

	@RequestMapping(value = "/searchThisUser", method = RequestMethod.POST)
	public ModelAndView searchThisUser(@RequestParam("username") String name)

	{
		logger.info("User " + getPrincipal() + " searched for " + name);

		ModelAndView model = new ModelAndView();

		model.setViewName("viewUserDetails");
		List<User> user = new ArrayList<User>();
		try {
			user = genService.searchUserByName(name); // / fetch a list of users
														// by the firstname
			model.addObject("forUser",name);
			model.addObject("user", user);

			return model;
		} catch (CollegeException e) {
			logger.error("Error while fetching user due to " + e.getMessage());
			logger.error(e);
			model.addObject("error", "No Users Found");
			return model;
		} catch (SQLException e) {
			logger.error("Error while fetching user due to " + e.getMessage());
			logger.error(e);

			model.addObject("error", "No Users Found");
			return model;
		} catch (Exception e) {

			model.addObject("error", "No Users Found");
			logger.error("Error while fetching user due to " + e.getMessage());
			logger.error(e);
			return model;
		}
	}

	// Render a page having authorization error if the user is not authorized to
	// access resources defined in SecurityCheck.java

	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDenied() {
		logger.fatal("User " + getPrincipal()
				+ " tried accessing restricted resource");

		return "Access_Denied";
	}

	// fetch the username from the spring factory having the user session
	// details of the logged in user.
	private String getPrincipal() {
		String username = null;
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal(); // get details of the
														// logged in user
		if (principal instanceof UserDetails) {

			username = ((UserDetails) principal).getUsername(); // fetching the
																// username
		} else {
			username = principal.toString();
		}

		return username;
	}

	// logout method

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request,
			HttpServletResponse response) {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {

			logger.info("User " + getPrincipal() + " logged out");
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

}
