<%@page import="quizweb.QuizWebConstants"%>
<%@page import="quizweb.StatisticsManager"%>
<%@page import="quizweb.SummaryStatistics"%>
<%@page import="java.util.List"%>
<%@page import="com.mysql.jdbc.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Summary Statistics</title>
<style>
table,th,td
{
border:1px solid black;
}
</style>
<script type="text/javascript" src="sortable.js"></script>
</head>
<body>
<h2>Full Quiz Summary Statistics</h2>

<table class="sortable" id="anyid">
     <tr>      
     	 <th>User</th>
         <th>Mean Score</th>
         <th>Median Score</th>
         <th>High Score</th>
         <th>Times Taken</th>
     </tr>
<% 
Connection conn = (Connection) session.getAttribute("connection");
int quizId = Integer.parseInt(request.getParameter("quiz_id"));
List<SummaryStatistics> summaryStatistics = StatisticsManager.getSummaryStatistics(conn, quizId, 0);
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

	<p><a href="HomepageServlet">Back to Homepage</a></p>

</body>
</html>