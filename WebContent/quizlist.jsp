<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz List</title>
</head>
<body>
<h1>List of Quizzes:</h1>
<ul>
<% 
	try {
		ServletContext sc = request.getServletContext();
		java.sql.Statement stmt = (java.sql.Statement) sc.getAttribute("StatementObject");
		java.sql.ResultSet rs = (java.sql.ResultSet) stmt.executeQuery("SELECT quiz_id, title FROM quizzes");
		while (rs.next()) {
			String title = rs.getString("title");
			String id = rs.getString("quiz_id");
		%>
			<li><a href="QuizSummaryServlet?quiz_id=<%=id %>"><%=title %></a></li>
		<%
		}
	} catch (java.sql.SQLException e) {
		e.printStackTrace();
	}
%>
<p><a href="HomepageServlet">Back to Homepage</a></p>
</body>
</html>