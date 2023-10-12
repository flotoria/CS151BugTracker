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

public class ProjectDAO {
	private Connection conn = null;
	
	public ProjectDAO() {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			System.out.println("ProjectDAO: Database connected to.");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	
	public void createProjectRecord(ProjectBean project) {
		String date = project.getStartingDate().toString();
		String name = project.getProjectName();
		String description = project.getProjectDescription();
		
		String sql = String.format("INSERT INTO " + "ProjectTable" 
					+ " (name, startingDate, description)"
					+ " VALUES ('%s', '%s', '%s');", name, date, description);
		try {
			Statement statement = conn.createStatement();
			statement.execute(sql);
			System.out.println("Project record created.");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	
	
	
}
