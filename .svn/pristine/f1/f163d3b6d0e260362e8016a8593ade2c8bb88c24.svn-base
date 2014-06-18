package quizweb;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MultiPageServlet
 */
@WebServlet("/MultiPageServlet")
public class MultiPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MultiPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get servlet context
		ServletContext sc = request.getServletContext();
		HttpSession session = request.getSession();
		Map<Integer, Boolean> gradeMap = (Map<Integer, Boolean>) session.getAttribute("GradeMap");
		
		// store answer
		String idStr = request.getParameter("idStr");
		String answer = request.getParameter(idStr);
		int id = Integer.parseInt(idStr);
		sc.setAttribute(idStr, answer);
		
		// get index and last index
		List<Integer> questionIds = (List<Integer>) sc.getAttribute("QuestionIdArray");
		int index = (Integer) sc.getAttribute("index");
		int lastIndex = questionIds.size()-1;
		request.setAttribute("LastIndex", lastIndex);
		
		// if immedi is on, grade and show result, else go to next question
		String nextPage;
		String immedi = (String) sc.getAttribute("immedi");
		if (immedi.equals("on")) {
			// grading
			Grader grader = (Grader) sc.getAttribute("Grader");
			int score = (Integer) sc.getAttribute("score");
			try {
				boolean correct = grader.grade(id, answer);
				if (correct) {
					score++;
					sc.setAttribute("score", score);
					gradeMap.put(id,true);
				} else {
					gradeMap.put(id,false);
				}
				request.setAttribute("question_id", idStr);
				request.setAttribute("correct", correct);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();	
			}			
			nextPage = "immedi_grade.jsp";			
		} else {
			// increment index until reaching the end of the array
			if (index == lastIndex) {
				nextPage = "GradeQuizServlet";
			} else {
				index++;
				sc.setAttribute("index", index);
				nextPage = "take_multi_page_quiz.jsp";
			}				
		}
		
		// forward
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
