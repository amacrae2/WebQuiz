<%@page import="quizweb.MySQLUtil"%>
<%@page import="quizweb.AchievementManager"%>
<%@page import="quizweb.QuizWebConstants"%>
<%@page import="quizweb.HistoryManager"%>
<%@page import="quizweb.HistoryRow"%>
<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="java.util.*" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.sql.ResultSet" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Result</title>
<style>
table,th,td
{
border:1px solid black;
}
</style>
<script type="text/javascript" src="sortable.js"></script>
</head>
<body>
	<%
	// get servlet context
	ServletContext sc = request.getServletContext();
	Map<Integer, Boolean> gradeMap = (Map<Integer, Boolean>) session.getAttribute("GradeMap");
	
	// get score
	int score = (Integer) sc.getAttribute("score");
	String percentCorr = (String) request.getAttribute("percentCorr");
	String time = (String) request.getAttribute("TimeTaken");
	String date = (String) request.getAttribute("DateTaken");
	int quizId = (Integer) request.getAttribute("quiz_id");
	%>	
	<p>Your Score: <%=score %></p>
	<p>Percent Correct: <%=percentCorr %>%</p>
	<p>Time Used: <%=time %></p>
	<p>Date: <%=date %></p>
	<table class="asortable">
		<tr>
			<th>Your Answer</th>
			<th>Correct Answers</th>
			<th>True or False</th>
		</tr>
	<% 	
	List<Integer> questionIds = (List<Integer>) sc.getAttribute("QuestionIdArray");
	Statement stmt = (Statement) sc.getAttribute("StatementObject");
	for (int i=0; i<questionIds.size(); i++) {
		// get the question
		int id = questionIds.get(i);
		ResultSet rs = stmt.executeQuery("SELECT * FROM questions WHERE  question_id="+id);
		rs.next();
		
		// get answers and correct answers	
		String answer = (String) sc.getAttribute(Integer.toString(id));
		String corrAnswer = rs.getString("answer");	
		boolean corr = gradeMap.get(id);
		
		// remove attribute for the question ??
		sc.removeAttribute(Integer.toString(id));
		sc.removeAttribute("EndTime");
	%>
		<tr>
			<td><%=answer %></td>
			<td><%=corrAnswer %></td>
			<td><%=corr %></td>
		</tr>
	<%
	}	
	%>
</table class="sortable" id="anyid">

<%
Connection conn = (Connection) session.getAttribute("connection");
String username = (String) session.getAttribute("user"); 
boolean practice = (Boolean) request.getAttribute("practice");
if (practice && !AchievementManager.achievementAlreadyExists(conn, QuizWebConstants.PRACTICE_MAKES_PERFECT, username)) {
	AchievementManager.addAchievement(conn, QuizWebConstants.PRACTICE_MAKES_PERFECT, username);
	%>
	<h3>You Earned The Achievement <%=QuizWebConstants.PRACTICE_MAKES_PERFECT%>!</h3>
	<%
}
if (AchievementManager.quizRookie(conn, username)) {
	AchievementManager.addAchievement(conn, QuizWebConstants.QUIZ_ROOKIE, username);
	%>
	<h3>You Earned The Achievement <%=QuizWebConstants.QUIZ_ROOKIE%>!</h3>
	<%
}
if (AchievementManager.quizGuru(conn, username)) {
	AchievementManager.addAchievement(conn, QuizWebConstants.QUIZ_GURU, username);
	%>
	<h3>You Earned The Achievement <%=QuizWebConstants.QUIZ_GURU%>!</h3>
	<%
}
if (AchievementManager.quizMachine(conn, username)) {
	AchievementManager.addAchievement(conn, QuizWebConstants.QUIZ_MACHINE, username);
	%>
	<h3>You Earned The Achievement <%=QuizWebConstants.QUIZ_MACHINE%>!</h3>
	<%
}
if (AchievementManager.iAmTheGreatest(conn, username, quizId)) {
	AchievementManager.addAchievement(conn, QuizWebConstants.I_AM_THE_GREATEST, username);
	%>
	<h3>You Earned The Achievement <%=QuizWebConstants.I_AM_THE_GREATEST%>!</h3>
	<%
}
%>

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

<% 
Map<String, Object> params = new HashMap<String, Object>();
params.put("username_1", username);
List<String> friendsList = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "username_2", "friends", params, true);
if (friendsList.size() > 0) {
	%>

<p>Friends Scores:</p>
<table class="sortable" id="anyid">
     <tr>      
     	 <th>User</th>
         <th>Score</th>
         <th>Percent Correct</th>
         <th>Time Taken</th>
         <th>Date Taken</th>
     </tr>
<% 
List<HistoryRow> friendsScores = HistoryManager.getQuizScoreHistory(conn, quizId, QuizWebConstants.NUM_ROWS_BEFORE_CUTOFF, username, true, false);
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

<p><a href=<%=friendsScoresUrl%>>See Full List of Friends Scores</a></p>


		<form action="MessageServlet" method="post">
		<input type="hidden" name="quizId" value=<%=quizId %>>		
		<p>Choose A Friend: <select name="FriendSelector">
		<%
		try {
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
		%></select><input type="submit" name="button" value="Challenge Friend"></p>
	</form>
	<%
}
	
%>
	
	<p><a href="HomepageServlet">Back to Homepage</a></p>
</body>
		