<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='spring'
	uri='http://www.springframework.org/tags/form'%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<link rel="stylesheet" href="mycss.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration Page</title>
</head>
<body>

	<div id="main">

		<div id="container">
			<p>User Registration Page</p>
			<div id="error">${error}</div>

			<div id="success">${success}</div>
			<spring:form commandName="user" method="post"
				action="/CollegeManagementSystem/registerTheUser">
				<table>
					<tr>
						<td>FirstName<span><spring:errors path='firstname' /></span></td>
					</tr>
					<tr>
						<td><spring:input path="firstname" /></td>
					</tr>
					<tr>
						<td>LastName<span><spring:errors path='lastname' /></span></td>
					</tr>
					<tr>
						<td><spring:input path="lastname" /></td>
					</tr>
					<tr>
						<td>Please choose a username<span><spring:errors
									path='username' /></span></td>
					</tr>
					<tr>
						<td><spring:input path="username" /></td>
					</tr>
					<tr>
						<td>Password<span><spring:errors path='password' /></span></td>
					</tr>
					<tr>
						<td><spring:password path="password" /></td>
					</tr>
					<tr>
						<td>Email<span><spring:errors path='email' /></span></td>
					</tr>
					<tr>
						<td><spring:input path="email" /></td>
					</tr>

					<tr>
						<td>Address<span><spring:errors path='address' /></span></td>
					</tr>
					<tr>
						<td><spring:input path="address" /></td>
					</tr>
					<tr>
						<td>Contact<span><spring:errors path='contact' /></span></td>
					</tr>
					<tr>
						<td><spring:input path="contact" /></td>
					</tr>

					<tr>
						<td>Question 1<span><spring:errors
									path='question.question1' /></span></td>
					</tr>
					<tr>
						<td><spring:input path="question.question1" /></td>
					</tr>
					<tr>
						<td>Answer <span><spring:errors
									path='question.answer1' /></span></td>
					</tr>
					<tr>
						<td><spring:input path="question.answer1" /></td>
					</tr>
					<tr>
						<td>Question 2<span><spring:errors
									path='question.question2' /></span></td>
					</tr>
					<tr>
						<td><spring:input path="question.question2" /></td>
					</tr>
					<tr>
						<td>Answer <span><spring:errors
									path='question.answer2' /></span></td>
					</tr>
					<tr>
						<td><spring:input path="question.answer2" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="submit" value="Register"></td>
					</tr>
				</table>

			</spring:form>

		</div>

	</div>

	Already a User?
	<a href="updateDetails">Login</a>

</body>
</html>