package quizweb;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class UserPageServlet
 */
@WebServlet("/UserPageServlet")
public class UserPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) request.getParameter("user");
		request.setAttribute("user", username);
		HttpSession session = request.getSession();
		Connection conn = (Connection) session.getAttribute("connection");
		setAttributesForUsersRecentlyCreatedQuizzes(conn, request, username);
		setAttributesForUsersRecentlyTakenQuizzes(conn, request, username);
		setAttributesForUserHistory(conn, request, username);

		
		RequestDispatcher dispatch = request.getRequestDispatcher("user_page.jsp"); 
		dispatch.forward(request, response); 
	}

	private void setAttributesForUserHistory(Connection conn, HttpServletRequest request,
			String username) {
		List<HistoryRow> userHistory = HistoryManager.getUserHistory(conn, username, 5);
		request.setAttribute("userHistory", userHistory);
	}

	private void setAttributesForUsersRecentlyTakenQuizzes(Connection conn, 
			HttpServletRequest request, String username) {
		Map<String,Object> params = new HashMap<String,Object>(); 
		params.put("username", username);
		List<Integer> recentlyTakenQuizzes = (List<Integer>)(List<?>) MySQLUtil.getQuery(conn, "quiz_id", "quizzes_taken", params, true, "quiz_id");
		List<String> quizTakenTimes = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "date_taken", "quizzes_taken", params, true, "quiz_id");
		List<Integer> recentlyTakenQuizIds = filterQuizIdsForUniqueness(recentlyTakenQuizzes);
		String quizIds = convertQuizIdsToString(recentlyTakenQuizIds);
		updateParamsForRecentQuizzesQuery(params, quizIds);
		List<String> recentlyTakenQuizNames = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "title", "quizzes", params, false);
		request.setAttribute("recentlyTakenQuizNames", recentlyTakenQuizNames);
		request.setAttribute("recentlyTakenQuizIds", recentlyTakenQuizIds);
	}

	private void updateParamsForRecentQuizzesQuery(Map<String, Object> params,
			String quizIds) {
		params.remove("username");
		if (quizIds.length() < 1) {
			params.put("quiz_id", 0);
		} else {
			params.put("quiz_id", quizIds.substring(0, quizIds.length()-1));
		}
	}

	private String convertQuizIdsToString(List<Integer> recentlyTakenQuizIds) {
		String quizIds = "";
		for (int id: recentlyTakenQuizIds) {
			quizIds += Integer.toString(id)+",";
		}
		return quizIds;
	}

	private List<Integer> filterQuizIdsForUniqueness(
			List<Integer> recentlyTakenQuizzes) {
		List<Integer> recentlyTakenQuizIds = new ArrayList<Integer>();
		HashSet<Integer> lookup = new HashSet<Integer>();
		for (int i: recentlyTakenQuizzes) {
		    if (lookup.add(i)) {
		        // Set.add returns false if item is already in the set
		    	recentlyTakenQuizIds.add(i);
		    }
		}
		return recentlyTakenQuizIds;
	}

	private void setAttributesForUsersRecentlyCreatedQuizzes(Connection conn, HttpServletRequest request, String username) {
		Map<String,Object> params = new HashMap<String,Object>(); 
		params.put("author", username);
		String quizNameAttributeId = "recentlyCreatedUserQuizNames";
		String quizIdArrtributeId = "recentlyCreatedUserQuizIds";
		String quizCreateTimeArrtributeId = "recentlyCreatedUserQuizTimes";
		setAttributesForRecentlyCreatedQuizzes(conn, request, params, quizNameAttributeId, quizIdArrtributeId, quizCreateTimeArrtributeId);
	}

	private void setAttributesForRecentlyCreatedQuizzes(Connection conn, HttpServletRequest request,
			Map<String, Object> params, String quizNameAttributeId,
			String quizIdArrtributeId, String quizCreateTimeArrtributeId) {
		List<String> recentlyCreatedQuizNames = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "title", "quizzes", params, true, "date DESC");
		List<Integer> recentlyCreatedQuizIds = (List<Integer>)(List<?>) MySQLUtil.getQuery(conn, "quiz_id", "quizzes", params, true, "date DESC");
		List<String> quizCreateTimes = (List<String>)(List<?>) MySQLUtil.getQuery(conn, "date", "quizzes", params, true, "date DESC");
		request.setAttribute(quizNameAttributeId, recentlyCreatedQuizNames);
		request.setAttribute(quizIdArrtributeId, recentlyCreatedQuizIds);
		request.setAttribute(quizCreateTimeArrtributeId, quizCreateTimes);
	}




}
