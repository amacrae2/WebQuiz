package quizweb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Connection;

public class HistoryManager {
	
	public static List<HistoryRow> getQuizScoreHistory(Connection conn, int quizId, int numRows, String username, boolean friendTable, boolean userTable) {
		return getQuizScoreHistory(conn, quizId, numRows, username, friendTable, userTable, "score DESC,time_taken");
	}
	
	public static List<HistoryRow> getQuizScoreHistory(Connection conn, int quizId, int numRows, String username, boolean friendTable, boolean userTable, String orderBy) {
		List<HistoryRow> quizHistory = new ArrayList<HistoryRow>();
		List<List<Object>> history = generateTopScores(conn, quizId, username, userTable, orderBy);
		for (int i = 0; i < history.size(); i++) {
			addHistoryRowToQuizHistory(conn, quizId, quizHistory, history, i, username, friendTable);
		}
		if (numRows > 0 && quizHistory.size() >= numRows) {
			quizHistory = quizHistory.subList(0, numRows);
		}
		return quizHistory;
	}

	

	public static List<HistoryRow> getUserHistory(Connection conn, String username, int numRows, int quizId) {
		List<HistoryRow> userHistory = new ArrayList<HistoryRow>();
		List<List<Object>> history = generateHistoryInfo(conn, username);
		for (int i = 0; i < history.size(); i++) {
			addHistoryRowToUserHistory(conn, username, userHistory, history, i);
		}
		if (numRows > 0 && userHistory.size() >= numRows) {
			userHistory = userHistory.subList(0, numRows);
		}
		return userHistory;
	}

	
	public static List<HistoryRow> getUserHistory(Connection conn, String username, int numRows) {
		return getUserHistory(conn,username, numRows, 0);
	}
	
	public static List<HistoryRow> getUserHistory(Connection conn, String username) {
		return getUserHistory(conn,username, 0, 0);
	}
	
	private static void addHistoryRowToQuizHistory(Connection conn, int quizId,
			List<HistoryRow> quizHistory, List<List<Object>> history, int i, String username, boolean friendTable) {
		List<Object> row = history.get(i);
		boolean addRow = true;
		String name = (String) row.get(0);
		int score = (Integer) row.get(1);
		String timeTaken = (String) row.get(2);
		String dateTaken = (String) row.get(3);
		String percentCorr = (String) row.get(4) + "%";
		if (friendTable) {
			addRow = determineIfFriend(conn, username, name);
		}
		if (addRow) {
			Map<String,Object> params = new HashMap<String,Object>(); 
			params.put("quiz_id", quizId);
			List<String> quizTitle = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "title", "quizzes", params, true); 
			HistoryRow hRow = new HistoryRow(score, quizId, (String)quizTitle.get(0), dateTaken, name, timeTaken, percentCorr);
			quizHistory.add(hRow);
		}
	}

	private static boolean determineIfFriend(Connection conn, String username, String name) {
		List<String> friendsList = getFriendsList(username, conn);
		if (!friendsList.contains(name)) {
			return false;
		}
		return true;
	}
	
	private static void addHistoryRowToUserHistory(Connection conn, String username,
			List<HistoryRow> userHistory, List<List<Object>> history, int i) {
		List<Object> row = history.get(i);
		Map<String,Object> params = new HashMap<String,Object>(); 
		params.put("quiz_id", row.get(1));
		List<String> quizTitle = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "title", "quizzes", params, true); 
		int score = (Integer) row.get(0);
		int id = (Integer) row.get(1);
		String dateTaken = (String) row.get(2);
		String percentCorr = (String) row.get(3) + "%";
		HistoryRow hRow = new HistoryRow(score,id,(String)quizTitle.get(0),dateTaken, username, percentCorr);
		userHistory.add(hRow);
	}
	
	private static List<List<Object>> generateTopScores(Connection conn, int quizId, String username, boolean userTable, String orderBy) {
		Map<String,Object> params = new HashMap<String,Object>(); 
		params.put("quiz_id", quizId);
		if (userTable) {
			params.put("username", username);
		}
		List<List<Object>> history = (List<List<Object>>)(List<?>) MySQLUtil.getQuery(conn,"username,score,time_taken,date_taken,percent_corr", "quizzes_taken", params, true, orderBy);
		return history;
	}

	private static List<List<Object>> generateHistoryInfo(Connection conn, String username) {
		Map<String,Object> params = new HashMap<String,Object>(); 
		params.put("username", username);
		List<List<Object>> history = (List<List<Object>>)(List<?>) MySQLUtil.getQuery(conn,"score,quiz_id,date_taken,percent_corr", "quizzes_taken", params, true, "date_taken DESC");
		return history;
	}
	
	private static List<String> getFriendsList(String username, Connection conn) {
		Map<String,Object> params = new HashMap<String,Object>(); 
		params.put("username_1", username);
		List<String> friendsList = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "username_2", "friends", params, true);
		return friendsList;
	}
	
	
}
