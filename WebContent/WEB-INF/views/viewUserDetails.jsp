<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

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

			<jsp:include page="header.jsp" />

<p> Viewing Results for search User:${forUser}</p>

			<div id="error">${error}</div>

			<div id="success">${success}</div>
<form>


			<table>
				<tr>
					<td>FirstName</td>
					<td>Lastname</td>

					<td>EmailAddress</td>
					<td>Contact</td>




				</tr>

				<core:forEach items="${user}" var="user">
					<tr>
						<td>${user.firstname}</td>
						<td>${user.lastname}</td>
						<td>${user.email}</td>
						<td>${user.contact}</td>
					<tr>
				</core:forEach>


			</table>

		</form>
		</div>
	</div>

</body>
</html>