package application.data_access_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import application.java_beans.CommentBean;

public class CommentDAO {
	/** An instance of Connection for accessing the database*/
	private Connection conn = null;
	
	/**
	 * Connects with the database
	 */
	public CommentDAO() {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			System.out.println("CommentDAO: Database connected to.");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the connections
	 * @return	the Connection of CommentDAO
	 */
	public Connection getConnection() {
		return conn;
	}
	
	/**
	 * Creates a new project record in the database using the identifiers provided by the TicketBean
	 * @param comment	the CommontBean of the project that contains its name and description
	 */
	public void createTicketRecord(CommentBean comment) {
		String date = comment.getStartingDate().toString();
		String description = comment.getCommentDescription();
		
		String sql = String.format("INSERT INTO " + "CommentTable" 
					+ " (startingDate, description)"
					+ " VALUES ('%s', '%s');", date, description);
		try {
			Statement statement = conn.createStatement();
			statement.execute(sql);
			System.out.println("Ticket record created.");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}		
}
