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
				String dateString = set.getString("startingDate");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate localDate = LocalDate.parse(dateString, formatter);
				list.add(new ProjectBean(set.getString("name"),	localDate, set.getString("description")));
			}

			conn.close();
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ProjectBean fetchProjectByName(String name) {

		String filterByProjectName = String.format("SELECT * FROM ProjectTable \n"
									+ "WHERE name = '%s'", name);
		ProjectBean project = null;
		
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery(filterByProjectName);
			
			String dateString = set.getString("startingDate");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(dateString, formatter);
			project = new ProjectBean(set.getString("name"), localDate, set.getString("description"));
			
			System.out.println("TicketDAO: Project name " + name + " fetched.");
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return project;
	}	
	
	public int fetchProjectIDByName(String name) {

		String filterByProjectName = String.format("SELECT * FROM ProjectTable \n"
									+ "WHERE name = '%s'", name);
		int id = -1;
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery(filterByProjectName);
			id = set.getInt("id");
			System.out.println("MainDAO: Project ID fetched from name - " + id);
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}	
	
	public ArrayList<String> fetchTicketNamesByProjectID(int id) {

		String filterByProjectID = String.format("SELECT * FROM TicketTable \n"
									+ "WHERE ProjectID=%s", id);

		ArrayList<String> ticketNames = new ArrayList<String>();
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery(filterByProjectID);
			while (set.next()) {
				ticketNames.add(set.getString("name"));
			}
			System.out.println("MainDAO: Tickets fetched from id - " + id);
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ticketNames;
	}	
	
}
