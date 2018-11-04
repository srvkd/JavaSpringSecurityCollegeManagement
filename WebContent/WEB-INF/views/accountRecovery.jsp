<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Account recovery</title>

<link rel="stylesheet" href="mycss.css" type="text/css">
</head>
<body>
	<div id="main">

		<div id="container">


			<p> Account Recovery<br></br>
			You will have to answer the security questions you entered while
			registering and also an OTP which will be sent to you on your email.
			</p>

			<div id="error">${error}</div>
		
			<div id="success">${success}</div>
			<form action="/CollegeManagementSystem/findUser" method="POST">

				<table>
					<tr>
						<td>Please enter your username</td>
					</tr>
					<tr>
						<td><input type="text" name="username"></td>
					</tr>
					<tr>
						<td><input type="submit" value="Submit"></td>
					</tr>


				</table>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />

			</form>


		</div>

	</div>


</body>
</html>