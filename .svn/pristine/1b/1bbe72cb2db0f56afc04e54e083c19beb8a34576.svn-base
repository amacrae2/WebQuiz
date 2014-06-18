package quizweb;

import java.sql.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class DatabaseConnectionListener
 *
 */
@WebListener
public class DatabaseConnectionListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public DatabaseConnectionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	ServletContext sc = arg0.getServletContext();
    	
    	// initialize database connection
        DatabaseConnection dbc = new DatabaseConnection();
        Statement stmt = dbc.getStatement();   
        sc.setAttribute("StatementObject", stmt);
        
        // initialize account manager        
        AccountManager am = new AccountManager(stmt);
        sc.setAttribute("AccountManager", am);
        
        // initialize quiz creator        
        QuizCreator qc = new QuizCreator(stmt);
        sc.setAttribute("QuizCreator", qc);
        
        // initialize quiz grader        
        Grader grader = new Grader(stmt);
        sc.setAttribute("Grader", grader);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
