<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix='spring'
	uri='http://www.springframework.org/tags/form'%>

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
<p>Please reset your Password</p>

			<spring:form commandName="user" action="/CollegeManagementSystem/resetPassword" method="POST">

				<table>

					<tr>
						<td>New Password<span><spring:errors
									path='password' /></span></td>
					</tr>
					<tr>
						<td><spring:password path="password"/></td>
					</tr>
					<tr>
						<td>Confirm Password</td>
					</tr>
					<tr>
						<td><input type="password" name="confirmPass"></td>
					</tr>
					<tr>
						<td><input type="submit" value="Reset Now"></td>
					</tr>

				</table>

					<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
			</spring:form>
		</div>

	</div>

</body>
</html>