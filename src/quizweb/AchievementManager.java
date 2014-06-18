package quizweb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AchievementManager {

	public static void addAchievement(Connection conn, String achievement, String username) {
		String sqlQuery = "INSERT INTO achievements (title,username) VALUES ('"+achievement+"','"+username+"');";
		Statement stmt = null;
		if(conn!=null){
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(sqlQuery);
			} catch (SQLException e) {
			    System.out.println("SQLException: " + e.getMessage());
			}
		}
	}
	
	public static List<String> getAchievements(Connection conn, String username) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		List<String> achievements = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "title", "achievements", params, true, "title");
		return achievements;
	}
	
	public static boolean amateurAuthor(Connection conn, String username) {
		int size = QuizWebConstants.AMATUER_AUTHOR_NUM;
		String title = QuizWebConstants.AMATUER_AUTHOR;
		return testNumQuizzesCreated(conn, username, size, title);
	}
	
	public static boolean prolificAuthor(Connection conn, String username) {
		int size = QuizWebConstants.PROLIFIC_AUTHOR_NUM;
		String title = QuizWebConstants.PROLIFIC_AUTHOR;
		return testNumQuizzesCreated(conn, username, size, title);
	}
	
	public static boolean prodigiousAuthor(Connection conn, String username) {
		int size = QuizWebConstants.PRODIGIOUS_AUTHOR_NUM;
		String title = QuizWebConstants.PRODIGIOUS_AUTHOR;
		return testNumQuizzesCreated(conn, username, size, title);
	}
	
	public static boolean quizRookie(Connection conn, String username) {
		int size = QuizWebConstants.QUIZ_ROOKIE_NUM;
		String title = QuizWebConstants.QUIZ_ROOKIE;
		return testNumQuizzesTaken(conn, username, size, title);
	}

	public static boolean quizGuru(Connection conn, String username) {
		int size = QuizWebConstants.QUIZ_GURU_NUM;
		String title = QuizWebConstants.QUIZ_GURU;
		return testNumQuizzesTaken(conn, username, size, title);
	}
	
	public static boolean quizMachine(Connection conn, String username) {
		int size = QuizWebConstants.QUIZ_MACHINE_NUM;
		String title = QuizWebConstants.QUIZ_MACHINE;
		return testNumQuizzesTaken(conn, username, size, title);
	}
	
	public static boolean iAmTheGreatest(Connection conn, String username, int quizId) {
		List<HistoryRow> quizHistory = HistoryManager.getQuizScoreHistory((com.mysql.jdbc.Connection) conn, quizId, 1, username, false, false);
		if (quizHistory.size() == 0) {
			return false;
		}
		HistoryRow topScore = quizHistory.get(0);
		String topScorer = topScore.getUsername();
		if (topScorer.equals(username) && !achievementAlreadyExists(conn, QuizWebConstants.I_AM_THE_GREATEST, username)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean achievementAlreadyExists(Connection conn, String achievement, String username) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		List<String> achievements = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "title", "achievements", params, true);
		if (achievements.contains(achievement)) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean testNumQuizzesTaken(Connection conn, String username, int size, String title) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		List<Object> quizzes = MySQLUtil.getQuery(conn, "id", "quizzes_taken", params, true);
		if (quizzes.size() >= size && !achievementAlreadyExists(conn, title, username)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean testNumQuizzesCreated(Connection conn, String username, int size, String title) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("author", username);
		List<Object> quizzes = MySQLUtil.getQuery(conn, "quiz_id", "quizzes", params, true);
		if (quizzes.size() >= size && !achievementAlreadyExists(conn, title, username)) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
