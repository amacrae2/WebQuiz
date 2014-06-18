<%@page import="java.util.Locale"%>
<%@page import="quizweb.DateTimeManager"%>
<%@page import="quizweb.QuizWebConstants"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="quizweb.HistoryManager"%>
<%@page import="quizweb.HistoryRow"%>
<%@page import="java.util.List"%>
<%@page import="com.mysql.jdbc.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Past Day</title>
<style>
table,th,td
{
border:1px solid black;
}
</style>
<script type="text/javascript" src="sortable.js"></script>
</head>
<body>
<h2>Full List of Top Performers in the Past Day</h2>

<table class="sortable" id="anyid">
     <tr>      
     	 <th>User</th>
         <th>Score</th>
         <th>Percent Correct</th>
         <th>Time Taken</th>
         <th>Date Taken</th>
     </tr>
<% 
Connection conn = (Connection) session.getAttribute("connection");
String username = (String) session.getAttribute("user");
int quizId = Integer.parseInt(request.getParameter("quiz_id"));
List<HistoryRow> quizHistory = HistoryManager.getQuizScoreHistory(conn, quizId, 0, username, false, false);
for (int i = 0; i < quizHistory.size(); i++) {
	HistoryRow hRow = quizHistory.get(i);
	String dateTakenString = hRow.getDateTaken();
	Date dateTaken = new SimpleDateFormat(QuizWebConstants.DATE_TIME_FORMAT, Locale.ENGLISH).parse(dateTakenString);
	Date oneDayAgo = DateTimeManager.getDateForOneDayAgo();
	if (dateTaken.after(oneDayAgo)) {
		%>
	     <tr>      
	         <td><%=hRow.getUsername()%></td>
	         <td><%=hRow.getScore()%></td>
	         <td><%=hRow.getPercentCorrect()%></td>
	         <td><%=hRow.getTimeTaken()%></td>
	         <td><%=hRow.getDateTaken()%></td>
	     </tr>
	 <%
	}
 } %>
</table>
</html>