<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix='spring'
	uri='http://www.springframework.org/tags/form'%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body>
	<div id="main">

		<div id="container">

			<jsp:include page="header.jsp" />
<p> Change Your password </p>

			<div id="error">${error}</div>

			<div id="success">${success}</div>
			<spring:form commandName="password"
				action="/CollegeManagementSystem/changeThePassword" method="POST">
				<input type="hidden" name="username" value="${username}" />
				<table>
					<tr>
						<td>Current password<span><spring:errors
									path='oldPassword' /></span></td>
					</tr>
					<tr>
						<td><spring:password path="oldPassword" /></td>
					</tr>

					<tr>
						<td>New Password<span><spring:errors
									path='newPassword' /></span></td>
					</tr>

					<tr>
						<td><spring:password path="newPassword" /></td>
					</tr>
					<tr>
						<td>Confirm Password<span><spring:errors
									path='confirmPassword' /></span></td>
					</tr>

					<tr>
						<td><spring:password path="confirmPassword" /></td>
					</tr>
					<tr>

						<td><input type="submit" value="Change" /></td>
					</tr>
				</table>


				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />

			</spring:form>


		</div>

	</div>


</body>

</html>