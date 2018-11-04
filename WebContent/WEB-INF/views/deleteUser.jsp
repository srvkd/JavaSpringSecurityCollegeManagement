<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<div id="main">

		<div id="container">

			<jsp:include page="header.jsp" />
			<p>Delete User</p>


			<div id="error">${error}</div>

			<div id="success">${success}</div>

			<table>
				<tr>
					<td>Username</td>
					<td>Contact</td>

					<td>EmailAddress</td>
					<td></td>


				</tr>

				<core:forEach items="${users}" var="user">
					<tr>
						<form action="/CollegeManagementSystem/deleteThisUser"
							method="POST">

							<input type="hidden" name="id" value="${user.user_id}" />
						<td>${user.username}</td>
						<td>${user.contact}</td>
						<td>${user.email}</td>

						<td><input type="submit" value="Delete" /></td>

					</tr>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />

					</form>
					<tr>
				</core:forEach>


			</table>


		</div>
	</div>

</body>
</html>