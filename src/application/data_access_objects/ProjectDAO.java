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
            System.out.println("Database created successfully or is already created.");
            createProjectTable();
            System.out.println("Project table created successfully or is already created.");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createProjectTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS ProjectTable (\n"
				+ "id INTEGER NOT NULL PRIMARY KEY,\n" 
				+ "name TEXT, \n" 
				+ "startingDate DATE, \n" 
				+ "description TEXT \n"
				+ ");";
		
		if (conn != null) {
			Statement statement = conn.createStatement();
			statement.execute(sql);
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
	
	public ArrayList<String> fetchAllProjects() {
		String sql = "SELECT * from ProjectTable";
		ArrayList<ProjectBean> list = new ArrayList<ProjectBean>();
		
		ArrayList<String> stringList = new ArrayList<String>();
		try {
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery(sql);
			
			String dateString = set.getString("startingDate");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(dateString, formatter);
			
			while (set.next()) {
				list.add(new ProjectBean(set.getString("name"),	localDate, set.getString("description")));
			}
			
			for (ProjectBean s : list) {
				stringList.add(s.getProjectName());
			}
		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return stringList;
	}
	
	
}
