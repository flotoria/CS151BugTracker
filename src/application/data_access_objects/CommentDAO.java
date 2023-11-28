package application.data_access_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import application.java_beans.CommentBean;
import application.java_beans.ProjectBean;
import application.java_beans.TicketBean;

/**
 * The Data Access Object for the CommentController class
 */
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
	 * Creates a new comment record in the database using the identifiers provided by the CommentBean
	 * @param comment	the CommentBean of the project that contains its name and description
	 */
	public void createCommentRecord(CommentBean comment) {
		
		LocalDateTime timestamp = comment.getTimestamp();
		Timestamp sqlTimestamp = Timestamp.valueOf(timestamp);
		
		String text = comment.getCommentText();
		text = text.replace("'", "''");
		int ticketID = comment.getTicket().getTicketID();
		
		String sql = String.format("INSERT INTO " + "CommentTable" 
					+ " (text, timestamp, TicketID)"
					+ " VALUES ('%s', '%s', %s);",  text, sqlTimestamp.toString(), ticketID);
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			statement.execute(sql);
			System.out.println("Comment record created.");
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}		
	

	
	/**
	 * Fetches a comment by searching for their unique ticket ID
	 * @param id	the unique ID of a ticket
	 */
	public ArrayList<CommentBean> fetchCommentsByTicket(TicketBean ticket) {

		ArrayList<CommentBean> list = new ArrayList<CommentBean>();
		int ticketID = ticket.getTicketID();
		
		String filterByTicketID = String.format("SELECT * FROM CommentTable \n"
				+ "WHERE TicketID=%s", ticketID);
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery(filterByTicketID);
			while (set.next()) {
				Timestamp sqlTime = set.getTimestamp("timestamp");
				LocalDateTime javaTime = sqlTime.toLocalDateTime();
				String text = set.getString("text");
				
				list.add(new CommentBean(text, javaTime, ticket));
			}
			
			System.out.println("CommentDAO: Comments fetched.");
			set.close();
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}	
	
	public void deleteTicketByID(int id) {
		deleteCommentsByTicketID(id);
		String sql = "DELETE FROM TicketTable WHERE id = ?";
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
            pstmt.executeUpdate();
			System.out.println("Deleted");
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteCommentsByTicketID(int id) {
		
		String sql = "DELETE FROM CommentTable WHERE TicketID = ?";
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
            pstmt.executeUpdate();
			System.out.println("Deleted");
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
