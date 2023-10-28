package application.data_access_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import application.java_beans.ProjectBean;
import application.java_beans.TicketBean;

/**
 * The Data Access Object for the MainController class
 */
public class MainDAO {
	/** An instance of Connection for accessing the database*/
	private Connection conn = null;
	
	/**
	 * Connects with the database and creates a project table
	 */
	public MainDAO() { 
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
	        System.out.println("MainDAO: Database created successfully or is already created.");
	        createProjectTable();
	        System.out.println("MainDAO: Project table created successfully or is already created.");
	        createTicketTable();
	        System.out.println("MainDAO: Ticket table created successfully or is already created.");
	        createCommentTable();
	        System.out.println("MainDAO: Comment table created successfully or is already created.");
	        conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a project table storing project id, name, starting date, and description, unless it already exists
	 * @throws SQLException	Connection conn is not defined
	 */
	private void createProjectTable() throws SQLException {
		conn = DriverManager.getConnection("jdbc:sqlite:database.db");
		String sql = "CREATE TABLE IF NOT EXISTS ProjectTable (\n"
				+ "id INTEGER NOT NULL PRIMARY KEY,\n" 
				+ "name TEXT, \n" 
				+ "startingDate DATE, \n" 
				+ "description TEXT \n"
				+ ");";
	
		
		if (conn != null) {
			Statement statement = conn.createStatement();
			statement.execute(sql);
			conn.close();
		}
	}
	
	/**
	 * Creates a ticket table storing ticket id and ticket description, unless it already exists
	 * @throws SQLException	Connection conn is not defined
	 */
	private void createTicketTable() throws SQLException {
		conn = DriverManager.getConnection("jdbc:sqlite:database.db");
		String sql = "CREATE TABLE IF NOT EXISTS TicketTable (\n"
				+ "id INTEGER NOT NULL PRIMARY KEY,\n" 
				+ "name TEXT, \n" 
				+ "description TEXT, \n"
				+ "ProjectID INTEGER, \n"
				+ "FOREIGN KEY (ProjectID) REFERENCES ProjectTable(id) \n"
				+ ");";
		
		if (conn != null) {
			Statement statement = conn.createStatement();
			statement.execute(sql);
			conn.close();
		}
	}
	
	/**
	 * Creates a comment table storing comment text, description, and timestamp, unless it already exists
	 * @throws SQLException	Connection conn is not defined
	 */
	private void createCommentTable() throws SQLException {
		conn = DriverManager.getConnection("jdbc:sqlite:database.db");
		String sql = "CREATE TABLE IF NOT EXISTS CommentTable (\n"
				+ "id INTEGER NOT NULL PRIMARY KEY,\n" 
				+ "text TEXT, \n"
				+ "timestamp TIMESTAMP, \n"
				+ "TicketID INTEGER, \n"
				+ "FOREIGN KEY (TicketID) REFERENCES TicketTable(id) \n"
				+ ");";
		
		if (conn != null) {
			Statement statement = conn.createStatement();
			statement.execute(sql);
			conn.close();
		}
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
	
	/**
	 * Accesses all projects from the database and returns as an ArrayList
	 * @return	the ArrayList of projects
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

			conn.close();
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	

	public ArrayList<TicketBean> fetchTicketsByProjectID(int id) {

		String filterByProjectID = String.format("SELECT * FROM TicketTable \n"
									+ "WHERE ProjectID=%s", id);

		ArrayList<TicketBean> ticketList = new ArrayList<TicketBean>();
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery(filterByProjectID);
			while (set.next()) {
				ticketList.add(new TicketBean(set.getString("name"), set.getString("description"), fetchProjectByProjectID(id), set.getInt("id")));
			}
			System.out.println("MainDAO: Tickets fetched from id - " + id);
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ticketList;
	}	
	
}
