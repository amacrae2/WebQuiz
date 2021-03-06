package quizweb;

import java.io.IOException;
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
 * Servlet implementation class CreateQuestionServlet
 */
@WebServlet("/CreateQuestionServlet")
public class CreateQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestionServlet() {
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
		HttpSession session = request.getSession();
		QuizCreator qc = (QuizCreator) sc.getAttribute("QuizCreator");
		int quizId = (Integer) sc.getAttribute("create_quiz_id");
						
		// get the question type
		String questionType = (String) request.getParameter("QuestionType");
		int type = Integer.parseInt(questionType);
		
		// store the question by calling question creator
		try {
			String title = request.getParameter("question_title");
			switch (type) {
				case 1: 
					String text1 = request.getParameter("question_text");
					String answer1 = request.getParameter("correct_answers");	
					qc.createQuestionResponse(type, quizId, title, text1, answer1);
					break;
				case 2: 
					String text2 = request.getParameter("question_text");
					String answer2 = request.getParameter("correct_answers");
					qc.createFillBlank(type, quizId, title, text2, answer2);
					break;
				case 3: 
					String text3 = request.getParameter("question_text");
					String choice3 = request.getParameter("multi_choices");
					String answer3 = request.getParameter("correct_choices");
					qc.createMultiChoice(type, quizId, title, text3, choice3, answer3);
					break;
				case 4: 
					String text4 = request.getParameter("question_text");
					String url4 = request.getParameter("picture_url");
					String answer4 = request.getParameter("correct_answers");
					qc.createPictureResponse(type, quizId, title, text4, url4, answer4);
					break;
				case 5: 
					String text5 = request.getParameter("question_text");
					String order = request.getParameter("multi_answer_order");
					String answer5 = request.getParameter("correct_answers");
					qc.createMultiAnswer(type, quizId, title, text5, answer5, order);
					break;
				case 6: 
					String text6 = request.getParameter("question_text");
					String answer6 = request.getParameter("correct_answers");
					qc.createFillBlank(type, quizId, title, text6, answer6);
					break;
				default: break;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		// get the next step option
		String nextStep = request.getParameter("add_or_back");
		String nextPage;
		int numQuestions = (Integer) session.getAttribute("numQuestions");
		numQuestions++;
		session.setAttribute("numQuestions", numQuestions);
		if (nextStep.equals("add")) {
			nextPage="create_question_type.jsp";
		} else {
			Statement stmt = (Statement) sc.getAttribute("StatementObject");
			try {
				stmt.executeUpdate("UPDATE quizzes SET num_questions='"+numQuestions+"' WHERE quiz_id='"+quizId+"'");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nextPage="HomepageServlet";
			//sc.removeAttribute("current_quiz_id");
		}
			
		// forward to the next page
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}
}
