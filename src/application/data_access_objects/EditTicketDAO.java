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
			System.out.println("EditTicketDAO: Database connected to.");
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
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			statement.execute(sql);
			System.out.println("EditTicketDAO: Ticket record edited.");
			conn.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}		
	
	public TicketBean fetchTicketByTicketID(int id) {

		String filterByTicketID = String.format("SELECT * FROM TicketTable \n"
									+ "WHERE id=%s", id);

		TicketBean ticket = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery(filterByTicketID);
			ticket = new TicketBean(set.getString("name"), set.getString("description"), fetchProjectByProjectID(set.getInt("ProjectID")), set.getInt("id"));
			set.close();
			
			System.out.println("EditTicketDAO: Ticket fetched from id - " + id);
			conn.close();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ticket;
	}
	
	/**
	 * Fetches a project by searching for their unique ID
	 * @param id	the unique ID of a project
	 */
	public ProjectBean fetchProjectByProjectID(int id) {
		
		String filterByProjectID = String.format("SELECT * FROM ProjectTable \n"
				+ "WHERE id=%s", id);
		
		ProjectBean obj = null; 
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery(filterByProjectID);
			
			int projectID = set.getInt("id");
			String dateString = set.getString("startingDate");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(dateString, formatter);
			obj = (new ProjectBean(set.getString("name"), localDate, set.getString("description"), projectID));
			set.close();
			conn.close();
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	

	
}
