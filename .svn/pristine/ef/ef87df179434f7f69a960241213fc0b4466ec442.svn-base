<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Outbox</title>
</head>
<body>
<h1>Message Outbox:</h1>
<ul>
<% 
	try {
		ServletContext sc = request.getServletContext();
		String user = (String) session.getAttribute("user");
		java.sql.Statement stmt = (java.sql.Statement) sc.getAttribute("StatementObject");
		java.sql.ResultSet rs = (java.sql.ResultSet) stmt.executeQuery("SELECT id, to_username, subject FROM messages WHERE from_username='"+user+"';");
		while (rs.next()) {
			String id = rs.getString("id");
			String to = rs.getString("to_username");
			String sub = rs.getString("subject");
		%>
			<li>To: <%=to %>; Subject: <a href="message.jsp?id=<%=id %>"><%=sub %></a></li>
		<%
		}
	} catch (java.sql.SQLException e) {
		e.printStackTrace();
	}
%>
<p><a href="HomepageServlet">Back to Homepage</a></p>
</body>
</html>