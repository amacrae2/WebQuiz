<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Friend Result</title>
</head>
<body>
<h1>Search Result:</h1>
<% 
// get statement object from servlet context
ServletContext sc = request.getServletContext();
Statement stmt = (Statement) sc.getAttribute("StatementObject");

//search for the account name in database
String target = (String) request.getParameter("SearchTarget");
ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username='"+target+"';");

// show the result
String user = (String) session.getAttribute("user");
if (rs.next()) {
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
} else {
	%>
	<p>This name doesn't exist.</p>
	<%
}
%>
<p><a href="HomepageServlet">Back to Homepage</a></p>
</body>
</html>