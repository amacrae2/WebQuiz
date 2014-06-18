<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Account</title>
</head>
<body>
<h1>The Name ${username} is Already in Use</h1>
<p>Please enter another name and password</p>
<form action="CreateServlet" method="post">
	<p>User Name: <input type="text" name="username"></p>
	<p>Password: <input type="text" name="password"><input type="submit" value="Login"></p>
</form>
</body>
</html>