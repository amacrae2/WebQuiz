<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="quizweb.QuizWebConstants"%>
<%@page import="quizweb.HistoryManager"%>
<%@page import="quizweb.HistoryRow"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Friends Quiz History</title>
<style>
table,th,td
{
border:1px solid black;
}
</style>
<script type="text/javascript" src="sortable.js"></script>
</head>
<body>
<h2>Full Friends Score History</h2>

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
List<HistoryRow> friendsScores = HistoryManager.getQuizScoreHistory(conn, quizId, 0, username, true, false);
String friendsScoresUrl = "friends_scores_list.jsp?quiz_id="+quizId;
for (int i = 0; i < friendsScores.size(); i++) {
	HistoryRow hRow = friendsScores.get(i);
	%>
     <tr>      
         <td><%=hRow.getUsername()%></td>
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