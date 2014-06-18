package quizweb;

import java.io.IOException;
//import java.io.PrintWriter;


import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		// get the account manager
		ServletContext sc = request.getServletContext();
		AccountManager am = (AccountManager) sc.getAttribute("AccountManager");
		
		// get the parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// check the parameters
		try {
			if (am.checkAccountExists(username) && am.checkPassword(username, password)) {
				// store username in session ??
				HttpSession session = request.getSession();
				session.setAttribute("user", username);
				// switch to welcome page ??	
				request.setAttribute("username", username);
				RequestDispatcher dispatch = request.getRequestDispatcher("HomepageServlet");
				dispatch.forward(request, response);
			} else {
				// switch to try again page
				RequestDispatcher dispatch = request.getRequestDispatcher("tryagain.html");
				dispatch.forward(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
