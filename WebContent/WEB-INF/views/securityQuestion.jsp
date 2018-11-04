<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix='spring'
	uri='http://www.springframework.org/tags/form'%>


<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="mycss.css" type="text/css">
</head>
<body>
	<div id="main">

		<div id="container">
		<p> Please Verify Your Security Questions </p>
		
			<spring:form method="post" commandName="questionBean"
				action="/CollegeManagementSystem/verifySecurityQuestions">
				<input type="text" name="username" value="${userId}" />
				<table>
					<tr>
						<td>Question 1</td>
					</tr>
					<tr>
						<td>${question.question1}</td>
					</tr>
					<tr>
						<td>Answer</td>
					</tr>
					<tr>
						<td><spring:password path="answer1" /></td>
					</tr>
					<tr>
						<td>Question 2</td>
					</tr>
					<tr>
						<td>${question.question2}</td>
					</tr>
					<tr>
						<td>Answer</td>
					</tr>
					<tr>
						<td><spring:password path="answer2" /></td>
					</tr>
					<tr>
						<td><input type="submit" value="Verify"></td>
					</tr>

				</table>

				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</spring:form>

		</div>

	</div>
</body>
</html>