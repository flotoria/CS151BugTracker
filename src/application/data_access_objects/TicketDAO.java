package application.data_access_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import application.java_beans.TicketBean;

public class TicketDAO {
	/** An instance of Connection for accessing the database*/
	private Connection conn = null;
	
	/**
	 * Connects with the database
	 */
	public TicketDAO() {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			System.out.println("TicketDAO: Database connected to.");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the connections
	 * @return	the Connection of TicketDAO
	 */
	public Connection getConnection() {
		return conn;
	}
	
	/**
	 * Creates a new project record in the database using the identifiers provided by the TicketBean
	 * @param ticket	the TicketBean of the project that contains its name, date, and description
	 */
	public void createTicketRecord(TicketBean ticket) {
		String date = ticket.getStartingDate().toString();
		String name = ticket.getTicketName();
		String description = ticket.getTicketDescription();
		
		String sql = String.format("INSERT INTO " + "TicketTable" 
					+ " (name, startingDate, description)"
					+ " VALUES ('%s', '%s', '%s');", name, date, description);
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
