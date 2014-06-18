package quizweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class QuizResultServlet
 */
@WebServlet("/QuizResultServlet")
public class QuizResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizResultServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		// get servlet context
		ServletContext sc = request.getServletContext();
		
		// store end time if not existing
		Object endTimeObj = (Object) sc.getAttribute("EndTime");
		if (endTimeObj==null) {
			long endTime = new Date().getTime();
			sc.setAttribute("EndTime", endTime);
		}	
		
		// get statement object and session
		Statement stmt = (Statement) sc.getAttribute("StatementObject");
		HttpSession session = request.getSession();
		
		// get date of taking activity
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		Date date = new Date();
		String dateStr = dateFormat.format(date);
		request.setAttribute("DateTaken", dateStr);
		
		// get time taken
		long startTime = (Long) sc.getAttribute("StartTime");
		long endTime = (Long) sc.getAttribute("EndTime");
		long timeTaken = endTime-startTime;
		String timeTakenStr = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeTaken), 
							TimeUnit.MILLISECONDS.toMinutes(timeTaken)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeTaken)),
							TimeUnit.MILLISECONDS.toSeconds(timeTaken)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTaken)));
		request.setAttribute("TimeTaken", timeTakenStr);
		
		// get the value for practice mode option
		String practice = (String) sc.getAttribute("practice_mode");
		// get parameters
		int score = (Integer) sc.getAttribute("score");
		String quizIdStr = (String) sc.getAttribute("take_quiz_id");
		int quizId = Integer.parseInt(quizIdStr);
		String username = (String) session.getAttribute("user");
		request.setAttribute("quiz_id", quizId);
		String percentCorr = "practice mode does not display a ";
		if (!(practice!=null)) {
			ResultSet rs;
			try {
				rs = stmt.executeQuery("SELECT num_questions FROM quizzes WHERE quiz_id='"+quizId+"';");
				rs.next();
				int numQuestions = rs.getInt("num_questions");
				DecimalFormat format = new DecimalFormat("#.##");
				percentCorr = format.format((double)score/numQuestions*100);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// store new entry in quizzes_taken table
			try {
				stmt.executeUpdate("INSERT INTO quizzes_taken (date_taken, score, time_taken, quiz_id, username, percent_corr) VALUES ('"+dateStr+"', '"+score+"', '"+timeTakenStr+"', '"
						+quizId+"', '"+username+"', '"+percentCorr+"');");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("practice", false);
		} else {
			request.setAttribute("practice", true);
		}
		request.setAttribute("percentCorr", percentCorr);
		
		// forward
		RequestDispatcher dispatch = request.getRequestDispatcher("quiz_result.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		// TODO Auto-generated method stub
	}
}
