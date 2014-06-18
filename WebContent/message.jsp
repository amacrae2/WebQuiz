<%@page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Message</title>
</head>
<body>
<%
// get message ID
String messageId = request.getParameter("id");

// get Statement Object
ServletContext sc = request.getServletContext();
Statement stmt = (Statement) sc.getAttribute("StatementObject");

// get user
String username = (String) session.getAttribute("user");

//get message info
String from = null;
String to = null;
String date = null;
String sub = null;
String content = null;
String viewed = null;
int type = 0;
int quizId = 0;
try {	
	ResultSet rs = (ResultSet) stmt.executeQuery("SELECT * FROM messages WHERE id='"+messageId+"'");
	rs.next();
	from = rs.getString("from_username");
	to = rs.getString("to_username");
	date = rs.getString("date");
	sub = rs.getString("subject");
	content = rs.getString("content");
	viewed = rs.getString("viewed");
	type = rs.getInt("type");
	quizId = rs.getInt("quiz_id");
	if (viewed.equals("unread") && username.equals(to)) {
		stmt.executeUpdate("UPDATE messages SET viewed='read' WHERE id='"+messageId+"'");
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}	
%>
<p>From: <%=from %></p>
<p>To: <%=to %></p>
<p>Date: <%=date %></p>
<p>Subject: <%=sub %></p>
<p>Message: <%=content %></p>
<%
switch (type) {
case 1: %>
	<form action='FriendServlet' method='post'>
		<input type='hidden' name='friend' value=<%=from %>>
		<input type="hidden" name="viewid" value="message.jsp?id=<%=messageId %>">
		<input type='submit' name='button' value='Accept'>
	</form>
<% break;
case 2: 
	ResultSet rs = (ResultSet) stmt.executeQuery("SELECT title FROM quizzes WHERE quiz_id='"+quizId+"';");		
	rs.next();
	String title = rs.getString("title");
	rs = stmt.executeQuery("SELECT score FROM quizzes_taken WHERE quiz_id='"+quizId+"' ORDER BY score DESC;");
	rs.next();
	String bestScore = rs.getString("score");
%>
	<p>Quiz title: <a href="QuizSummaryServlet?quiz_id=<%=quizId %>"><%=title %></a></p> 
	<p>Your friend's best score: <%= bestScore%></p>
<% break;
case 3: break;
default: break;
}	
%>
<p><a href="HomepageServlet">Back to Homepage</a></p>
</body>
</html>