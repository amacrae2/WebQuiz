package quizweb;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
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
		
		// get the parameter
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		request.setAttribute("username", username);
		
		// create the account
		try {
			if (am.checkAccountExists(username)) {
				// switch to name in use page
				RequestDispatcher dispatch = request.getRequestDispatcher("nameinuse.jsp");
				dispatch.forward(request, response);			
			} else if (am.createNewAccount(username, password)) {
				// store username in session
				HttpSession session = request.getSession();
				session.setAttribute("user", username);
				// switch to homepage
				RequestDispatcher dispatch = request.getRequestDispatcher("HomepageServlet");
				dispatch.forward(request, response);
			} else {
				// switch to create the account page
				RequestDispatcher dispatch = request.getRequestDispatcher("create.html");
				dispatch.forward(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

}
