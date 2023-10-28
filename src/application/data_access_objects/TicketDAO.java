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
 * The Data Access Object for the TicketController class
 */
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
	 * Creates a new ticket record in the database using the identifiers provided by the TicketBean
	 * @param ticket	the TicketBean of the project that contains its name, date, and description
	 */
	public void createTicketRecord(TicketBean ticket) {
		String name = ticket.getTicketName();
		String description = ticket.getTicketDescription();
		ProjectBean project = ticket.getProjectFromTicket();
	
	
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			String sql = String.format("INSERT INTO " + "TicketTable" 
					+ " (name, description, ProjectID)"
					+ " VALUES ('%s', '%s', %s);", name, description, project.getProjectID());
			statement.execute(sql);	
			conn.close();
			
			System.out.println("Ticket record created with " + name + ", " + description + ", " + project.getProjectID());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	

	/**
	 * Fetches a ticket by searching for their unique project ID
	 * @param id	the unique ID of a project
	 */
	public ProjectBean fetchProjectByID(int id) {

		String filterByProjectID = String.format("SELECT * FROM ProjectTable \n"
									+ "WHERE id = %s", id);
		ProjectBean project = null;
		
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery(filterByProjectID);
			
			String dateString = set.getString("startingDate");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(dateString, formatter);
			project = new ProjectBean(set.getString("name"), localDate, set.getString("description"));
			
			System.out.println("TicketDAO: Project ID " + id + " fetched.");
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return project;
	}	
	
	/**
	 * Accesses all projects from the database and returns as an ArrayList
	 * @return	the ArrayList of projects
	 */
	public ArrayList<ProjectBean> fetchAllProjects() {
		
		String sql = "SELECT * from ProjectTable";
		ArrayList<ProjectBean> list = new ArrayList<ProjectBean>();
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery(sql);
			
			while (set.next()) {
				int projectID = set.getInt("id");
				String dateString = set.getString("startingDate");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate localDate = LocalDate.parse(dateString, formatter);
				list.add(new ProjectBean(set.getString("name"),	localDate, set.getString("description"), projectID));
			}

			conn.close();
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
