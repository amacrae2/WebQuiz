<%@page import="quizweb.QuizWebConstants"%>
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
<title>Your Quiz History List</title>
<style>
table,th,td
{
border:1px solid black;
}
</style>
<script type="text/javascript" src="sortable.js"></script>
</head>
<body>
<h2>Your Full Quiz History</h2>

<table class="sortable" id="anyid">
     <tr>      
         <th>Score</th>
         <th>Percent Correct</th>
         <th>Time Taken</th>
         <th>Date Taken</th>
     </tr>
<% 
Connection conn = (Connection) session.getAttribute("connection");
String username = (String) session.getAttribute("user");   
int quizId = Integer.parseInt(request.getParameter("quiz_id"));
List<HistoryRow> userHistory = HistoryManager.getQuizScoreHistory(conn, quizId, 0, username, false, true);
String userQuizHistUrl = "user_quiz_history_list.jsp?quiz_id="+quizId;
for (int i = 0; i < userHistory.size(); i++) {
		HistoryRow hRow = userHistory.get(i);
		%>
	     <tr>      
	            <td><%=hRow.getScore()%></td>
         		<td><%=hRow.getPercentCorrect()%></td>
         		<td><%=hRow.getTimeTaken()%></td>
         		<td><%=hRow.getDateTaken()%></td>
	     </tr>
	 <% } %>
</table>

	<p><a href="HomepageServlet">Back to Homepage</a></p>

</body>
</html>