package quizweb;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.mysql.jdbc.Connection;

public class AdministrationManager {

	
	public static boolean isAdmin(Connection conn, String username) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("admin", "yes");
		List<String> users = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "username", "users", params, true);
		if(users.contains(username)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static List<String> getAnnouncements(Connection conn) {
		Date oneDayAgo = DateTimeManager.getDateForOneDayAgo();
		List<String> dates = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "date_created", "announcements");
		List<String> announcements = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "text", "announcements");
		List<Integer> indices = DateTimeManager.findIndicesOfDatesToRemove(oneDayAgo, dates);
		for (int i : indices) {
			announcements.remove(i);
		}
		return announcements;
	}
	
	public static void addAnnouncement(Connection conn, String announcement) {
		DateFormat dateFormat = new SimpleDateFormat(QuizWebConstants.DATE_TIME_FORMAT);
		Date date = new Date();
		String query = "INSERT INTO announcements (text,date_created) VALUES ('"+announcement+"','"+dateFormat.format(date)+"');";
		Statement stmt = null;
		if(conn!=null){
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(query);
			} catch (SQLException e) {
			    System.out.println("SQLException: " + e.getMessage());
			}
		}
	}
	
	public static int getNumberOfUsers(Connection conn) {
		List<String> users = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "username", "users");
		return users.size();
	}
	
	public static int getNumberOfQuizzes(Connection conn) {
		List<String> quizzes = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "title", "quizzes");
		return quizzes.size();
	}
	
	public static int getNumberOfQuizzesTaken(Connection conn) {
		List<String> quizzesTaken = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "id", "quizzes_taken");
		return quizzesTaken.size();
	}
	
	public static void promoteUser(Connection conn, String username) {
		String update = "'yes'";
		editUserAdminInfo(conn, username, update);
	}
	
	public static void demoteUser(Connection conn, String username) {
		String update = "null";
		editUserAdminInfo(conn, username, update);
	}

	private static void editUserAdminInfo(Connection conn, String username, String update) {
		String query = "UPDATE users SET admin="+update+" WHERE username='"+username+"';";
		Statement stmt = null;
		if(conn!=null){
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(query);
			} catch (SQLException e) {
			    System.out.println("SQLException: " + e.getMessage());
			}
		}
	}
	
	public static void clearQuizInfo(Connection conn, int quizId) {
		String quizzesTakenSqlQuery = "DELETE FROM quizzes_taken WHERE quiz_id = '"+quizId+"';";
		Statement stmt = null;
		if(conn!=null){
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(quizzesTakenSqlQuery);
			} catch (SQLException e) {
			    System.out.println("SQLException: " + e.getMessage());
			}
		}
	}
	
	public static void removeQuiz(Connection conn, int quizId) {
		String quizzesSqlQuery = "DELETE FROM quizzes WHERE quiz_id = '"+quizId+"';";
		String questionsSqlQuery = "DELETE FROM questions WHERE quiz_id = '"+quizId+"';";
		String quizzesTakenSqlQuery = "DELETE FROM quizzes_taken WHERE quiz_id = '"+quizId+"';";
		Statement stmt = null;
		if(conn!=null){
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(quizzesSqlQuery);
				stmt.executeUpdate(quizzesTakenSqlQuery);
				stmt.executeUpdate(questionsSqlQuery);
			} catch (SQLException e) {
			    System.out.println("SQLException: " + e.getMessage());
			}
		}
	}
	
	public static void removeUser(Connection conn, String username) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("author", username);
		List<Integer> quizIds = (List<Integer>)(List<?>) MySQLUtil.getQuery(conn, "quiz_id", "quizzes", params, true);
		String usersSqlQuery = "DELETE FROM users WHERE username = '"+username+"';";
		String quizzesSqlQuery = "DELETE FROM quizzes WHERE author = '"+username+"';";
		String quizzesTakenSqlQuery = "DELETE FROM quizzes_taken WHERE quiz_id = ";
		String questionsSqlQuery = "DELETE FROM questions WHERE quiz_id = ";
		quizzesTakenSqlQuery = addParamsToQueryString(quizIds, quizzesTakenSqlQuery);
		questionsSqlQuery = addParamsToQueryString(quizIds, questionsSqlQuery);
		String toMessageSqlQuery = "DELETE FROM messages WHERE to_username = '"+username+"';";
		String fromMessageSqlQuery = "DELETE FROM messages WHERE from_username = '"+username+"';";
		String friendOneSqlQuery = "DELETE FROM friends WHERE username_1 = '"+username+"';";
		String friendTwoSqlQuery = "DELETE FROM friends WHERE username_2 = '"+username+"';";
		String achievementsSqlQuery = "DELETE FROM achievements WHERE username = '"+username+"';";
		Statement stmt = null;
		if(conn!=null){
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(usersSqlQuery);
				stmt.executeUpdate(quizzesSqlQuery);
				stmt.executeUpdate(quizzesTakenSqlQuery);
				stmt.executeUpdate(questionsSqlQuery);
				stmt.executeUpdate(toMessageSqlQuery);
				stmt.executeUpdate(fromMessageSqlQuery);
				stmt.executeUpdate(friendOneSqlQuery);
				stmt.executeUpdate(friendTwoSqlQuery);
				stmt.executeUpdate(achievementsSqlQuery);
			} catch (SQLException e) {
			    System.out.println("SQLException: " + e.getMessage());
			}
		}
	}

	private static String addParamsToQueryString(List<Integer> quizIds, String query) {
		if (quizIds.size()==0) {
			query += "0";
		} else {
			for (int i = 0; i < quizIds.size(); i++) {
				query += "'"+quizIds.get(i)+"'";
				if (i != quizIds.size()-1) {
					query += " OR ";
				}
			}
		}
		query += ";";
		return query;
	}
	
}
