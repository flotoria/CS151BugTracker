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

/**
 * The Data Access Object for the ProjectController class
 */
public class EditProjectDAO {
	/** An instance of Connection for accessing the database*/
	private Connection conn = null;
	
	/**
	 * Connects with the database
	 */
	public EditProjectDAO() {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			System.out.println("ProjectDAO: Database connected to.");
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
	public void editProjectRecord(ProjectBean project) {
		String date = project.getStartingDate().toString();
		String name = project.getProjectName();
		name = name.replace("'", "''");
		String description = project.getProjectDescription();
		description = description.replace("'", "''");
		int id = project.getProjectID();
		
		String sql = String.format("UPDATE " + "ProjectTable" 
					+ " SET name='%s', startingDate='%s', description='%s'"
					+ " WHERE id=%s;", name, date, description, id);
		try {
			Statement statement = conn.createStatement();
			statement.execute(sql);
			System.out.println("Project record edited.");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}		
	
}
