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
			<jsp:include page="header.jsp" />

			<p> Search User By FirstName</p>

			<form action="/CollegeManagementSystem/searchThisUser" method="POST">

				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<table>
					<tr>
						<td>Firstname</td>
					</tr>
					<tr>
						<td><input type="text" name="username" /></td>
					</tr>
					<tr>
						<td><input type="submit" value="search" /></td>
					</tr>
				</table>

			</form>


		</div>
	</div>

</body>
</html>