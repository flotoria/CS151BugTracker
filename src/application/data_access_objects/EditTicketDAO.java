package application.data_access_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import application.java_beans.ProjectBean;
import application.java_beans.TicketBean;

/**
 * The Data Access Object for the ProjectController class
 */
public class EditTicketDAO {
	/** An instance of Connection for accessing the database*/
	private Connection conn = null;
	
	/**
	 * Connects with the database
	 */
	public EditTicketDAO() {
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
	 * @return	the Connection of ProjectDAO
	 */
	public Connection getConnection() {
		return conn;
	}
	
	/**
	 * Creates a new project record in the database using the identifiers provided by the ProjectBean
	 * @param project	the ProjectBean of the project that contains its name, date, and description
	 */
	public void editTicketRecord(TicketBean ticket) {
		String name = ticket.getTicketName();
		name = name.replace("'", "''");
		String description = ticket.getTicketDescription();
		description = description.replace("'", "''");
		int id = ticket.getTicketID();
		int projId = ticket.getProjectFromTicket().getProjectID();
		
		String sql = String.format("UPDATE " + "TicketTable" 
					+ " SET name='%s', description='%s', ProjectID='%s'"
					+ " WHERE id=%s;", name, description, projId, id);
		try {
			Statement statement = conn.createStatement();
			statement.execute(sql);
			System.out.println("Ticket record edited.");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}		
	
}
