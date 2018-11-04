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

<p> User Login Page</p>
			<div id="error">${SPRING_SECURITY_LAST_EXCEPTION.message}</div>
			<div id="success">${success }</div>
					
		


	<div id="login_form">




	<form action="/CollegeManagementSystem/login" method="POST">
	<table>
		<tr>
			<td>Usernme</td>
		</tr>
		<tr><td><input type="text" name="username">
	</td></tr>
		<tr>
			<td>Password</td>
		</tr>
		<tr><td><input type="password" name="password">
	
	</td></tr>
		
		
	<tr><td><input type="submit" value="Login"></td></tr>
	
	</table>
	
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form>



</div>
<a href="accountRecovery">
Forgot Password?
</a>
<br>
<a href="register">
Not a User Yet?</a>


</div>

</div>
</body>
</html>