<%@page import="java.sql.ResultSet"%>
<%@page import="com.mysql.jdbc.Statement"%>
<%@page import="com.mysql.jdbc.Connection"%>
<%@page import="quizweb.AdministrationManager"%>
<%@page import="quizweb.QuizWebConstants"%>
<%@page import="quizweb.AchievementManager"%>
<%@page import="quizweb.HistoryRow"%>
<%@page import="quizweb.MySQLUtil"%>
<%@ page import="java.util.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" /> 
<title>Home</title> 
<style>
table,th,td
{
border:1px solid black;
}
</style>
<script type="text/javascript" src="sortable.js"></script>
</head>
<body>
<% String username = (String) request.getAttribute("user");%>
<h1><%= username %>'s Page</h1>


<%
Connection conn = (Connection) session.getAttribute("connection"); 
String user = (String) session.getAttribute("user");
if (AdministrationManager.isAdmin(conn, user)) {
	%>
	<h2>Administrator Privileges</h2>
	<form action="RemoveUserServlet" method="post">
	<p><input type="submit" name="button" value="Remove User Account and Records From Website"></p>
	<input type="hidden" name="userToRemove" value=<%=username%>>
	</form>
	<%
	if (AdministrationManager.isAdmin(conn, username)) {
		%>
				<form action="AddAdminServlet" method="post">
				<p><input type="submit" name="button" value="Remove Administator Privileges"></p>
				<input type="hidden" name="userToAdd" value=<%=username%>>
				</form>
		<% 
	} else {
		%>
				<form action="AddAdminServlet" method="post">
				<p><input type="submit" name="button" value="Give Administator Privileges"></p>
				<input type="hidden" name="userToAdd" value=<%=username%>>
				</form>
		<% 
	}
}
%>
<br>
<%
// get statement object from servlet context
ServletContext sc = request.getServletContext();
Statement stmt = (Statement) sc.getAttribute("StatementObject");

// get the visitor's name from session
String visitorname = (String) session.getAttribute("user");

// check if the user is visitor's friend
ResultSet rs = stmt.executeQuery("SELECT * FROM friends WHERE username_1='"+visitorname+"' AND username_2='"+username+"' ;");
if (!rs.next()) {
	%>
	<form action="MessageServlet" method="post">
		<input type="hidden" name="friend" value=<%=username %>>
		<p><input type="submit" name="button" value="Send Friend Request"></p>
	</form>
	<%
} else {
	%>
	<form action="FriendServlet" method="post">
		<input type="hidden" name="friend" value=<%=username %>>
		<p><input type="submit" name="button" value="Remove Friend"></p>
	</form>
	<form action="MessageServlet" method="post">
		<input type="hidden" name="challenged_friend" value=<%=username %>>		
		<p>Choose A Quiz: <select name="QuizSelector">
		<%
		try {
			rs = stmt.executeQuery("SELECT quiz_id, title FROM quizzes");
			while (rs.next()) {
				String title = rs.getString("title");
				String id = rs.getString("quiz_id");
			%>
				<option value=<%=id %>><%=title %></option>
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

<h2><%= username %>'s Taken Quizzes:</h2>
<table class="sortable" id="anyid">
	<tr>
		<td><b>Quiz</b></td>
	</tr>
<% 
List<String> recentlyTakenQuizNames = (List<String>)(List<?>) request.getAttribute("recentlyTakenQuizNames");
List<Integer> recentlyTakenQuizIds = (List<Integer>)(List<?>) request.getAttribute("recentlyTakenQuizIds");
for (int i = 0; i < recentlyTakenQuizIds.size(); i++) {
	String quizUrl = "QuizSummaryServlet?quiz_id="+recentlyTakenQuizIds.get(i);
	%>
     <tr>      
         <td><a href=<%=quizUrl%>><%=recentlyTakenQuizNames.get(i)%></a></td>
     </tr>
 <% } %>
  </table>
    <br>
 <a href="quizlist.jsp">See All Quizzes</a>
 
 
<h2><%= username %>'s Created Quizzes:</h2>
<table class="sortable" id="anyid">
	<tr>
		<td><b>Quiz</b></td>
		<td><b>Date Created</b></td>
	</tr>
<% 
List<String> recentlyCreatedUserQuizNames = (List<String>)(List<?>) request.getAttribute("recentlyCreatedUserQuizNames");
List<Integer> recentlyCreatedUserQuizIds = (List<Integer>)(List<?>) request.getAttribute("recentlyCreatedUserQuizIds");
List<Integer> recentlyCreatedUserQuizCreateTimes = (List<Integer>)(List<?>) request.getAttribute("recentlyCreatedUserQuizTimes");
for (int i = 0; i < recentlyCreatedUserQuizIds.size(); i++) {
	String quizUrl = "QuizSummaryServlet?quiz_id="+recentlyCreatedUserQuizIds.get(i);
	%>
     <tr>      
         <td><a href=<%=quizUrl%>><%=recentlyCreatedUserQuizNames.get(i)%></a></td>
         <td><%=recentlyCreatedUserQuizCreateTimes.get(i)%></td>
     </tr>
 <% } %>
 </table>
 <br>
 <a href="quizlist.jsp">See All Quizzes</a>
 
 
<h2><%= username %>'s History:</h2>
<table class="sortable" id="anyid">
     <tr>      
         <td><b>Quiz</b></td>
         <td><b>Score</b></td>
         <td><b>Date Taken</b></td>
     </tr>
<% 
List<HistoryRow> userHistory = (List<HistoryRow>)(List<?>) request.getAttribute("userHistory");
String moreUrl = "HistoryList.jsp?user="+request.getAttribute("user");
for (int i = 0; i < userHistory.size(); i++) {
	HistoryRow hRow = userHistory.get(i);
	String quizUrl = "QuizSummaryServlet?quiz_id="+hRow.getQuizId();
	%>
     <tr>      
         <td><a href=<%=quizUrl%>><%=hRow.getQuizTitle()%></a></td>
         <td><%=hRow.getScore()%></td>
         <td><%=hRow.getDateTaken()%></td>
     </tr>
 <% } %>
 </table>
 <br>
 <a href=<%=moreUrl%>>See Full History</a>
 
<h2><%= username %>'s Achievements:</h2>
<dl>
<%
List<String> achievements = AchievementManager.getAchievements(conn, username);
for (int i = 0; i < achievements.size(); i++) {
	String achievement = achievements.get(i);
	String image = QuizWebConstants.ACHIEVEMENT_IMAGES.get(achievement);
	String tooltip = QuizWebConstants.ACHIEVEMENT_TOOLTIPS.get(achievement);
	%>
		<dd title=<%=tooltip%>><img src=<%=image%> width="40" height="40" title=<%=tooltip%>><%=achievement%></dd>
	<%
}
%>
</dl>


<p><a href="HomepageServlet">Back to Homepage</a></p>

</body>
</html>