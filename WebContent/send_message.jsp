<%@page import="java.sql.ResultSet" %>
<%@page import="java.sql.Statement" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Send Message</title>
</head>
<body>
<h1>Send a Message</h1>
<form action="MessageServlet" method="post">
	<p>To: <select name="UserSelector">
		<%
		ServletContext sc = request.getServletContext();
		Statement stmt = (Statement) sc.getAttribute("StatementObject");
		try {
			ResultSet rs = stmt.executeQuery("SELECT username FROM users");
			while (rs.next()) {
				String toName = rs.getString("username");
			%>
				<option value=<%=toName %>><%=toName %></option>
			<%
			}
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}		
		%></select></p>
	<p>Subject: <input type="text" name="subject"></p>
	<p>Message: <textarea rows="4" cols="50" name="content"></textarea></p>
	<p><input type="submit" name="button" value="Send A Message"></p>
</form>
<p><a href="HomepageServlet">Back to Homepage</a></p>
</body>
</html>