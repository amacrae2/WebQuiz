package quizweb;

import java.io.IOException;
import java.sql.SQLException;
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
 * Servlet implementation class GradeQuizServlet
 */
@WebServlet("/GradeQuizServlet")
public class GradeQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GradeQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get sevlet context
		ServletContext sc = request.getServletContext();
		HttpSession session = request.getSession();
		Map<Integer, Boolean> gradeMap = (Map<Integer, Boolean>) session.getAttribute("GradeMap");
			
		// store end time
		long endTime = new Date().getTime();
		sc.setAttribute("EndTime", endTime);
		
		// iterate through each question id
		int score = 0;
		List<Integer> questionIds = (List<Integer>) sc.getAttribute("QuestionIdArray");
		for (int i=0; i<questionIds.size(); i++) {
			// get id
			int id = questionIds.get(i);
			String idStr = Integer.toString(id);
			
			// get answer
			String answer;
			String multi = (String) sc.getAttribute("multi");
			if (multi.equals("on")) {
				answer = (String)sc.getAttribute(idStr);
			} else {
				answer = (String) request.getParameter(idStr);
				sc.setAttribute(idStr, answer);
			}	
			
			// get grader
			Grader grader = (Grader) sc.getAttribute("Grader");			
			try {
				if (grader.grade(id, answer)) {
					// add score
					score++;
					gradeMap.put(id,true);
				} else {
					gradeMap.put(id,false);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// store score
		sc.setAttribute("score", score);
		
		// forward
		RequestDispatcher dispatch = request.getRequestDispatcher("QuizResultServlet");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated constructor stub
	}

}
