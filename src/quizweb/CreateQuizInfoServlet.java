package quizweb;

import java.io.IOException;
import java.sql.SQLException;
import java.text.*;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * Servlet implementation class CreateQuizInfoServlet
 */
@WebServlet("/CreateQuizInfoServlet")
public class CreateQuizInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuizInfoServlet() {
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
		// get the quiz creator
		ServletContext sc = request.getServletContext();
		QuizCreator qc = (QuizCreator) sc.getAttribute("QuizCreator");
		
		// get the quiz author
		HttpSession session = request.getSession();
		String author = (String) session.getAttribute("user");
		int numQuestions = 0;
		session.setAttribute("numQuestions", numQuestions);
		
		// get date of creation
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		Date date = new Date();
		String dateStr = dateFormat.format(date);
				
		// get the parameters
		String quizTitle = request.getParameter("quiz_title");
		String quizDescription = request.getParameter("quiz_description");
		String randomQuestions = request.getParameter("random_questions");
		String multiPages = request.getParameter("multi_pages");
		String immediateCorr = request.getParameter("immediate_correction");
		
		// store quiz info by calling quiz creator
		try {
			int quizId = qc.createQuizInfo(quizTitle, author, dateStr, quizDescription, randomQuestions, multiPages, immediateCorr);
			sc.setAttribute("create_quiz_id", quizId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// forward to create question page
		RequestDispatcher dispatch = request.getRequestDispatcher("create_question_type.jsp");
		dispatch.forward(request, response);
	}

}
