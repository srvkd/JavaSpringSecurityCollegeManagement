<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<div id="main">
<div id="container">

<jsp:include page="header.jsp"/>


			<div id="error">${error}</div>
		
			<div id="success">${success}</div>
		
<div id="username">Welcome ${username}</div>		

</div>
</div>
</body>

</html>