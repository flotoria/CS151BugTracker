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

public class SearchTicketDAO {
	
	private Connection conn = null;
	/**
	 * Connects with the database
	 */
	public SearchTicketDAO() {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			System.out.println("SearchTicket: Database connected to.");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<TicketBean> fetchTicketsByName(String n) {

		String filterByProjectID = String.format("SELECT * FROM TicketTable");

		ArrayList<TicketBean> ticketList = new ArrayList<TicketBean>();
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery(filterByProjectID);
			while (set.next()) {
				String name = set.getString("name");
				if(name.contains(n)) ticketList.add(new TicketBean(set.getString("name"), set.getString("description"), fetchProjectByProjectID(set.getInt("ProjectID")), set.getInt("id")));
			}
			System.out.println("SearchTicketDAO: All tickets fetched with query '" + n + "'");
			set.close();
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ticketList;
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
	
	/**
	 * Fetches a ticket by searching for their unique project ID
	 * @param id	the unique ID of a project
	 */
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
			System.out.println("SearchTicketDAO: Tickets fetched from id - " + id);
			
			set.close();
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ticketList;
	}	
	
	/**
	 * Accesses all projects from the database containing a certain String in their title and returns as 
	 * an ArrayList
	 * @param n	the substring that should be contained by project names
	 * @return	the ArrayList of projects
	 */
	public ArrayList<ProjectBean> fetchCertainProjects(String n) {
		
		String sql = "SELECT * from ProjectTable";
		ArrayList<ProjectBean> list = new ArrayList<ProjectBean>();
		
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery(sql);
			
			while (set.next()) {
				String name = set.getString("name");
				if(name.contains(n)) {
					int projectID = set.getInt("id");
					String dateString = set.getString("startingDate");
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate localDate = LocalDate.parse(dateString, formatter);
					list.add(new ProjectBean(name, localDate, set.getString("description"), projectID));
				}
			}

			set.close();
			conn.close();
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
