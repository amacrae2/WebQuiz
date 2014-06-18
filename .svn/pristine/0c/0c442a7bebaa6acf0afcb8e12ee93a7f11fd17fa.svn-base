<%@page import="java.util.*" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Immediate Result</title>
</head>
<body>
	<% 	
	// get servlet context and statement object
	ServletContext sc = request.getServletContext();
	Statement stmt = (Statement) sc.getAttribute("StatementObject");
	
	// display the result and answers
	boolean correct = (Boolean) request.getAttribute("correct");
	String idStr = (String) request.getAttribute("question_id");
	ResultSet rs = stmt.executeQuery("SELECT answer FROM questions WHERE  question_id="+idStr);
	rs.next();
	String corrAnswer = rs.getString("answer");	
	String answer = (String) sc.getAttribute(idStr);					
	%>	
		<p>Your answer is <%=correct %></p>
		<table>
		<tr>
			<th>Your Answer</th>
			<th>Correct Answers</th>
		</tr>
		<tr>
			<td><%=answer %></td>
			<td><%=corrAnswer %></td>
		</tr>
		</table>
	<%
	// get index and last index
	int index = (Integer) sc.getAttribute("index");
	int lastIndex = (Integer) request.getAttribute("LastIndex");
	if (index==lastIndex) {
	%>
		<form action="QuizResultServlet" method="get">
			<p><input type="submit" value="See Your Report"></p>
		</form>
	<% 
	} else {
		 index++;
		 sc.setAttribute("index", index);
	%>
		<form action="take_multi_page_quiz.jsp" method="get">
			<p><input type="submit" value="Next Question"></p>
		</form>
	<%
	}
	%>	
</body>
		