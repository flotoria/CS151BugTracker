package application.data_access_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import application.java_beans.CommentBean;

/**
 * The Data Access Object for the ProjectController class
 */
public class EditCommentDAO {
	/** An instance of Connection for accessing the database*/
	private Connection conn = null;
	
	/**
	 * Connects with the database
	 */
	public EditCommentDAO() {
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
	 * @return	the Connection of ProjectDAO
	 */
	public Connection getConnection() {
		return conn;
	}
	
	/**
	 * Creates a new project record in the database using the identifiers provided by the ProjectBean
	 * @param project	the ProjectBean of the project that contains its name, date, and description
	 */
	public void editCommentRecord(CommentBean comment) {
		LocalDateTime timestamp = comment.getTimestamp();
		Timestamp sqlTimestamp = Timestamp.valueOf(timestamp);
		
		String text = comment.getCommentText();
		text = text.replace("'", "''");
		int ticketID = comment.getTicket().getTicketID();
		int id = comment.getCommentID();
		System.out.println(id);
		
		String sql = String.format("UPDATE " + "CommentTable" 
					+ " SET text='%s', timestamp='%s', TicketID='%s"
					+ " WHERE id=%s;", text, sqlTimestamp.toString(), ticketID, id);
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			statement.execute(sql);
			System.out.println("Comment record edited.");
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}		
	
}
