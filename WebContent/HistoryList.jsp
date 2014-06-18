<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="quizweb.HistoryRow"%>
<%@page import="quizweb.HistoryManager"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>History List</title>
<style>
table,th,td
{
border:1px solid black;
}
</style>
<script type="text/javascript" src="sortable.js"></script>
</head>
<body>
<h1>Full History for <%= request.getParameter("user") %>:</h1>
<table class="sortable" id="anyid">
     <tr>      
         <th>Quiz</th>
         <th>Score</th>
         <th>Percent Correct</th>
         <th>Date Taken</th>
     </tr>
<% 
	Connection conn = (Connection) session.getAttribute("connection");
	List<HistoryRow> userHistory = HistoryManager.getUserHistory(conn, request.getParameter("user"));
for (int i = 0; i < userHistory.size(); i++) {
	HistoryRow hRow = userHistory.get(i);
	String quizUrl = "QuizSummaryServlet?quiz_id="+hRow.getQuizId();
	%>
     <tr>      
         <td><a href=<%=quizUrl%>><%=hRow.getQuizTitle()%></a></td>
         <td><%=hRow.getScore()%></td>
         <td><%=hRow.getPercentCorrect()%></td>
         <td><%=hRow.getDateTaken()%></td>
     </tr>
 <% } %>
</table>
<p><a href="HomepageServlet">Back to Homepage</a></p>


</body>
</html>