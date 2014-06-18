package quizweb;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * Servlet implementation class FriendServlet
 */
@WebServlet("/FriendServlet")
public class FriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendServlet() {
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
		String user = (String) session.getAttribute("user");
		
		// check what button gets clicked
		String friend = null;
		try {
			String buttonName = request.getParameter("button");
			if (buttonName.equals("Remove Friend")) {
				friend = request.getParameter("friend");
				stmt.executeUpdate("DELETE FROM friends WHERE username_1='"+user+"' AND username_2='"+friend+"';");
				stmt.executeUpdate("DELETE FROM friends WHERE username_2='"+user+"' AND username_1='"+friend+"';");
				// switch to user page
				RequestDispatcher dispatch = request.getRequestDispatcher("UserPageServlet?user="+friend);
				dispatch.forward(request, response);
			} else {			
				friend = request.getParameter("friend");
				if (user.equals(friend)) {
					// switch to previous page
					RequestDispatcher dispatch = request.getRequestDispatcher(request.getParameter("viewid"));
					dispatch.forward(request, response);				
				} else {
					ResultSet rs = stmt.executeQuery("SELECT * FROM friends WHERE username_1='"+user+"' AND username_2='"+friend+"';");
					if (!rs.next()) { // so that when accept button is clicked multiple times, duplicate records won't be added
						stmt.executeUpdate("INSERT INTO friends (username_1, username_2) VALUES ('"+user+"', '"+friend+"');");			
						stmt.executeUpdate("INSERT INTO friends (username_2, username_1) VALUES ('"+user+"', '"+friend+"');");					
						// switch to user page
						RequestDispatcher dispatch = request.getRequestDispatcher("UserPageServlet?user="+friend);
						dispatch.forward(request, response);
					} else {
						// switch to friend exists page
						RequestDispatcher dispatch = request.getRequestDispatcher("friend_exist.jsp");
						dispatch.forward(request, response);
					}
				}		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
