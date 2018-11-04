
<html>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<head>
<link rel="stylesheet" href="mycss.css" type="text/css">
</head>

<body>

	<div id="header">
		<ul>
			<li class="left"><a href="search">Search User</a></li>
			
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<li class="left"><a href="deleteUser">Delete User</a></li>
				
			</sec:authorize>

			<li class="left"><a href="changePassword">Change Password</a></li>
			<li class="left"><a href="updateDetails">Edit Details</a></li>
			<li class="right"><a href="logout">Logout</a></li>

		</ul>


	</div>