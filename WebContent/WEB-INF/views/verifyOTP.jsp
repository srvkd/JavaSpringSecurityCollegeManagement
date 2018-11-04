<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<p>Enter the OTP that you received on your email.</p>
			<form action="/CollegeManagementSystem/verifyTheOTP" method="POST">
				<table>
					<tr>
						<td>OTP Please?</td>
					</tr>

					<tr>
						<td><input type="text" name="otp"></td>
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