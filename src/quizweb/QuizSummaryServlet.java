package quizweb;

import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuizSummaryServlet
 */
@WebServlet("/QuizSummaryServlet")
public class QuizSummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizSummaryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get quiz ID
		String quizId = request.getParameter("quiz_id");
		
		// get Statement Object
		ServletContext sc = request.getServletContext();
		Statement stmt = (Statement) sc.getAttribute("StatementObject");		
		
		ResultSet rs;
		try {
			// get quiz info
			rs = (ResultSet) stmt.executeQuery("SELECT * FROM quizzes WHERE quiz_id='"+quizId+"'");
			rs.next();
			String title = rs.getString("title");
			String author = rs.getString("author");
			String date = rs.getString("date");
			String description = rs.getString("description");
			int numQuestions = rs.getInt("num_questions");
			
			// pass on parameters
			sc.setAttribute("take_quiz_id", quizId);
			request.setAttribute("title", title);
			request.setAttribute("author", author);
			request.setAttribute("date", date);
			request.setAttribute("description", description);
			request.setAttribute("numQuestions",numQuestions);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		// forward to quiz summary page
		RequestDispatcher dispatch = request.getRequestDispatcher("quiz_summary.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
