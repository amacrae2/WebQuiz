<%@page import="quizweb.AdministrationManager"%>
<%@page import="quizweb.StatisticsManager"%>
<%@page import="quizweb.SummaryStatistics"%>
<%@page import="quizweb.DateTimeManager"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="quizweb.QuizWebConstants"%>
<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="quizweb.HistoryRow"%>
<%@page import="quizweb.HistoryManager"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.ResultSet" %>
<%@page import="java.sql.Statement" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Summary</title>
<style>
table,th,td
{
border:1px solid black;
}
</style>
<script type="text/javascript" src="sortable.js"></script>
</head>
<body>
<h1>Quiz Summary:</h1>
<% 
// get quiz info
ServletContext sc = request.getServletContext();
int quizId = Integer.parseInt(((String) sc.getAttribute("take_quiz_id")));
String title = (String) request.getAttribute("title");
String author = (String) request.getAttribute("author");
String date = (String) request.getAttribute("date");
String description = (String) request.getAttribute("description");
int numQuestions = (Integer) request.getAttribute("numQuestions");
String username = (String) session.getAttribute("user");
String authorUrl = "UserPageServlet?user="+author;
if (author.equals((String)session.getAttribute("user"))) {
	authorUrl = "HomepageServlet";
}
%>

<%
Connection conn = (Connection) session.getAttribute("connection");
if (AdministrationManager.isAdmin(conn, username)) {
	%>
	<h2>Administrator Privileges</h2>
	<form action="RemoveQuizServlet" method="post">
	<p><input type="submit" name="button" value="Remove Quiz"></p>
	<p><input type="submit" name="button" value="Clear Quiz History"></p>
	<input type="hidden" name="quizId" value=<%=quizId%>>
	</form>	
	<%
}
%>

<p>Quiz Title: <%=title %></p>
<p>Quiz Author: <a href = <%=authorUrl %>><%=author %></a></p>
<p>Date Created: <%=date %></p>
<p>Number Of Questions: <%=numQuestions %></p>
<p>Quiz Description: <%=description %></a></p>
<form action="TakeQuizServlet" method="post">
	<p><input type="checkbox" name="practice_mode">Practice Mode</p>
	<p><input type="submit" value="Start"></p>
</form>
<br>

<p>Your History:</p>
<table class="sortable" id="anyid">
     <tr>      
         <th>Score</th>
         <th>Percent Correct</th>
         <th>Time Taken</th>
         <th>Date Taken</th>
     </tr>
<% 
List<HistoryRow> userHistory = HistoryManager.getQuizScoreHistory(conn, quizId, QuizWebConstants.NUM_ROWS_BEFORE_CUTOFF, username, false, true);
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

<p><a href=<%=userQuizHistUrl%>>See Your Full Quiz History</a></p>


<p>Top Performers:</p>
<table class="sortable" id="anyid">
     <tr>      
     	 <th>User</th>
         <th>Score</th>
         <th>Percent Correct</th>
         <th>Time Taken</th>
         <th>Date Taken</th>
     </tr>
<% 
List<HistoryRow> quizHistory = HistoryManager.getQuizScoreHistory(conn, quizId, QuizWebConstants.NUM_ROWS_BEFORE_CUTOFF, username, false, false);
String quizHistUrl = "quiz_history_list.jsp?quiz_id="+quizId;
for (int i = 0; i < quizHistory.size(); i++) {
	HistoryRow hRow = quizHistory.get(i);
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

<p><a href=<%=quizHistUrl%>>See Full Quiz History</a></p>

<p>Top Performers in Past Day:</p>
<table class="sortable" id="anyid">
     <tr>      
     	 <th>User</th>
         <th>Score</th>
         <th>Percent Correct</th>
         <th>Time Taken</th>
         <th>Date Taken</th>
     </tr>
<% 
String quizHistPastDayUrl = "past_day_quiz_history_list.jsp?quiz_id="+quizId;
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

<p><a href=<%=quizHistPastDayUrl%>>See Full Quiz History for Past Day</a></p>

<p>Most Recent Test Takers:</p>
<table class="sortable" id="anyid">
     <tr>      
     	 <th>User</th>
         <th>Score</th>
         <th>Percent Correct</th>
         <th>Time Taken</th>
         <th>Date Taken</th>
     </tr>
<% 
List<HistoryRow> quizHistoryRecent = HistoryManager.getQuizScoreHistory(conn, quizId, QuizWebConstants.NUM_ROWS_BEFORE_CUTOFF, username, false, false, "date_taken DESC");
for (int i = 0; i < quizHistoryRecent.size(); i++) {
	HistoryRow hRow = quizHistoryRecent.get(i);
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

<p><a href=<%=quizHistUrl%>>See Full Quiz History</a></p>

<p>Quiz Summmary Statistics:</p>
<table class="sortable" id="anyid">
     <tr>      
     	 <th>User</th>
         <th>Mean Score</th>
         <th>Median Score</th>
         <th>High Score</th>
         <th>Times Taken</th>
     </tr>
<% 
List<SummaryStatistics> summaryStatistics = StatisticsManager.getSummaryStatistics(conn, quizId, QuizWebConstants.NUM_ROWS_BEFORE_CUTOFF+1);
String sumStatsUrl = "summary_statistics_list.jsp?quiz_id="+quizId;
for (int i = 0; i < summaryStatistics.size(); i++) {
	SummaryStatistics sumStats = summaryStatistics.get(i);
	%>
     <tr>      
         <td><%=sumStats.getUser()%></td>
         <td><%=sumStats.getMean()%></td>
         <td><%=sumStats.getMedian()%></td>
         <td><%=sumStats.getHighScore()%></td>
         <td><%=sumStats.getTimesTaken()%></td>
     </tr>
 <% } %>
</table>

<p><a href=<%=sumStatsUrl%>>See this Quiz's Summary Statistics for All Users</a></p>

<form action="MessageServlet" method="post">
<input type="hidden" name="quizId" value=<%=quizId %>>		
<p>Choose A Friend: <select name="FriendSelector">
<%
try {
	Statement stmt = (Statement) sc.getAttribute("StatementObject");
	ResultSet rs = stmt.executeQuery("SELECT username_2 FROM friends WHERE username_1='"+username+"';");
	while (rs.next()) {
		String friend = rs.getString("username_2");
		%>
			<option value=<%=friend %>><%=friend %></option>
		<%
	}
} catch (java.sql.SQLException e) {
	e.printStackTrace();
}		
%>
</select><input type="submit" name="button" value="Challenge Friend"></p>
</form>

<p><a href="HomepageServlet">Back to Homepage</a></p>
</body>
</html>