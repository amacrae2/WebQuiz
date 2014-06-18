<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="quizweb.AdministrationManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Site Statistics</title>
</head>
<body>
<h2>Site Statistics</h2>

<%
Connection conn = (Connection) session.getAttribute("connection");
int numQuizzes = AdministrationManager.getNumberOfQuizzes(conn);
int numUsers = AdministrationManager.getNumberOfUsers(conn);
int numQuizzesTaken = AdministrationManager.getNumberOfQuizzesTaken(conn);
%>

<ul>
	<li>Number of Registered Users: <%=numUsers %></li>
	<li>Number of Quizzes: <%=numQuizzes %></li>
	<li>Number of Times A Quiz has been Taken: <%=numQuizzesTaken %>
</ul>

<p><a href="HomepageServlet">Back to Homepage</a></p>
</body>
</html>