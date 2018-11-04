<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='spring'
	uri='http://www.springframework.org/tags/form'%>
	
	

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<link rel="stylesheet" href="mycss.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>



	<div id="main">

		<div id="container">
		
		
		<jsp:include page="header.jsp"/>

<p> Update your details</p>

			<div id="error">${error}</div>

			<div id="success">${success}</div>
			



			<spring:form commandName="user" method="post"
				action="/CollegeManagementSystem/updateMyDetails">
				<table>
					<tr>
						<td>FirstName<font color='red'><spring:errors
									path='firstname' /></font></td>
					</tr>
					<tr>
						<td><spring:input path="firstname" /></td>
					</tr>
					<tr>
						<td>LastName<font color='red'><spring:errors
									path='lastname' /></font></td>
					</tr>
					<tr>
						<td><spring:input path="lastname" /></td>
					</tr>
					
					<tr>
						<td>Email<font color='red'><spring:errors path='email' /></font></td>
					</tr>
					<tr>
						<td><spring:input path="email" /></td>
					</tr>
					
					<tr>
						<td>Address<font color='red'><spring:errors
									path='address' /></font></td>
					</tr>
					<tr>
						<td><spring:input path="address" /></td>
					</tr>
					<tr>
						<td>Contact<font color='red'><spring:errors
									path='contact' /></font></td>
					</tr>
					<tr>
						<td><spring:input path="contact" /></td>
					</tr>

					<tr>
						<td>Question 1<font color='red'><spring:errors
									path='question.question1' /></font></td>
					</tr>
					<tr>
						<td><spring:input path="question.question1" /></td>
					</tr><tr>
						<td>Answer <font color='red'><spring:errors
									path='question.answer1' /></font></td>
					</tr>
					<tr>
						<td><spring:input path="question.answer1" /></td>
					</tr><tr>
						<td>Question 2<font color='red'><spring:errors
									path='question.question2' /></font></td>
					</tr>
					<tr>
						<td><spring:input path="question.question2" /></td>
					</tr><tr>
						<td>Answer <font color='red'><spring:errors
									path='question.answer2' /></font></td>
					</tr>
					<tr>
						<td><spring:input path="question.answer2" /></td>
					</tr>
					<tr>
					
						<td colspan="2"><input type="submit" value="Update"></td>
					</tr>
				</table>

			</spring:form>

		</div>

	</div>


</body>
</html>