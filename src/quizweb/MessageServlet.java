package quizweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
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
		// get servlet context, statement object and session
		ServletContext sc = request.getServletContext();
		Statement stmt = (Statement) sc.getAttribute("StatementObject");
		HttpSession session = request.getSession();
		
		// get parameter
		String user = (String) session.getAttribute("user");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		Date date = new Date();
		String dateStr = dateFormat.format(date);
		
		// check what button gets clicked
		String query = null;
		String buttonName = request.getParameter("button");
		if (buttonName.equals("Send Friend Request")) {
			// send a friend request
			String subject = "Friend Request - from "+user;
			String friend = request.getParameter("friend");
			String friendRequest = "A user, "+user+", has sent you a friend request.";
			query = "INSERT INTO messages (from_username, to_username, content, date, subject, viewed, type) "
					+ "VALUES ('"+user+"', '"+friend+"', '"+friendRequest+"', '"
					+dateStr+"', '"+subject+"', 'unread', '1');";
		} else if (buttonName.equals("Challenge Friend")) {			
			String friend;
			String quizId;
			String subject = "Challenge Request - from "+user;
			
			if (request.getParameter("challenged_friend") != null) {
				// challenge by selecting quiz
				friend = request.getParameter("challenged_friend");
				quizId = request.getParameter("QuizSelector");	
			} else {
				// challenge by selecting friend
				quizId = request.getParameter("quizId");
				friend = request.getParameter("FriendSelector");		
			}
				
			String challengeRequest = "Your friend, "+user+", has sent you a challenge request.";
			query = "INSERT INTO messages (from_username, to_username, content, date, subject, viewed, type, quiz_id) VALUES ('"+user+"', '"+friend+"', '"+challengeRequest+"', '"
						+dateStr+"', '"+subject+"', 'unread', '2', '"+quizId+"');";	
		} else {
			// send a message
			String toUser = request.getParameter("UserSelector");
			String subject = request.getParameter("subject");	
			String content = request.getParameter("content");
			query = "INSERT INTO messages (from_username, to_username, content, date, subject, viewed, type) VALUES ('"+user+"', '"+toUser+"', '"+content+"', '"
					+dateStr+"', '"+subject+"', 'unread', '3');";
		}		
			
		// execute update
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setContentType("text/html; charset=UTF-8"); 
		PrintWriter out = response.getWriter(); 
		 
		out.println("<!DOCTYPE html>"); 
		out.println("<head>"); 
		out.println("<meta charset=\"UTF-8\" />"); 
		out.println("<title>Message Sent</title>"); 
		out.println("</head>"); 
		out.println("<body>"); 
	    out.println("<h1>Message has been sent.</h1>"); 
	    out.println("<p><a href='HomepageServlet'>Back to Homepage</a></p>");
		out.println("</body>"); 
		out.println("</html>"); 
	}

}
