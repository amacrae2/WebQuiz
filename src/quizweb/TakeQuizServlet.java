package quizweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TakeQuizServlet
 */
@WebServlet("/TakeQuizServlet")
public class TakeQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TakeQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get Statement Object
		ServletContext sc = request.getServletContext();
		Statement stmt = (Statement) sc.getAttribute("StatementObject");
		Map<Integer, Boolean> gradeMap = new HashMap<Integer, Boolean>(); 
		HttpSession session = request.getSession();
		session.setAttribute("GradeMap", gradeMap);
				
		// get quiz id and mode
		String quizId = (String) sc.getAttribute("take_quiz_id");
		String pracitceMode = request.getParameter("practice_mode"); 
		
		try {
			// get quiz options
			ResultSet rs = stmt.executeQuery("SELECT random, multi, immedi FROM quizzes WHERE  quiz_id="+quizId);
			rs.next();
			String random = rs.getString("random");
			String multi = rs.getString("multi");
			String immedi = rs.getString("immedi");
			
			// generate question id result set based on random option
			String query;
			if (random.equals("on")) {
				query = "SELECT question_id FROM questions WHERE  quiz_id="+quizId+" ORDER BY RAND()";
			} else {
				query = "SELECT question_id FROM questions WHERE  quiz_id="+quizId;
			}
			rs = stmt.executeQuery(query);
			
			// get an array of current question ids
			List<Integer> questionIds = new ArrayList<Integer>();
			try {
				while(rs.next()) {
					int id = rs.getInt("question_id");
					questionIds.add(id);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// pass on parameters
			sc.setAttribute("QuestionIdArray", questionIds);
			sc.setAttribute("practice_mode", pracitceMode);
			sc.setAttribute("multi", multi);
			sc.setAttribute("immedi", immedi);
					
			// switch to single or multiple page
			String nextPage;
			if (multi.equals("on")) {
				// initiate the index of the question array as 0	
				int index = 0;
				sc.setAttribute("index", index);
				if (immedi.equals("on")) {
					int score = 0;
					sc.setAttribute("score", score);
				}
				nextPage = "take_multi_page_quiz.jsp";
			} else {
				nextPage = "take_one_page_quiz.jsp";
			}
			
			// store starting time
			long startTime = new Date().getTime();
			sc.setAttribute("StartTime", startTime);
			
			// forward
			RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
			dispatch.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
