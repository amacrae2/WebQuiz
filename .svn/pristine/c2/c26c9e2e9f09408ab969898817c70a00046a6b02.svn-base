<%@page import="javax.websocket.Session"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Users List</title>
</head>
<body>
<h1>List of Users:</h1>
<form action="search_friend_result.jsp" method="post">
	<p>Search A Friend: <input type="text" name="SearchTarget"><input type="submit" value="Search"></p>
</form>
<ul>
<% 
	try {
		String user = (String) session.getAttribute("user");
		ServletContext sc = request.getServletContext();
		java.sql.Statement stmt = (java.sql.Statement) sc.getAttribute("StatementObject");
		java.sql.ResultSet rs = (java.sql.ResultSet) stmt.executeQuery("SELECT username FROM users;");
		while (rs.next()) {
			String name = rs.getString("username");
			if (name.equals(user)) {
				%>
				<li><a href="HomepageServlet"><%=name %></a></li>
				<%
			} else {
				%>
				<li><a href="UserPageServlet?user=<%=name %>"><%=name %></a></li>
				<%
			}
		}
	} catch (java.sql.SQLException e) {
		e.printStackTrace();
	}
%>
<p><a href="HomepageServlet">Back to Homepage</a></p>
</body>
</html>