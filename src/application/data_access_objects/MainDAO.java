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

public class MainDAO {
	
	private Connection conn = null;
	
	public MainDAO() { 
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:database.db");
	        System.out.println("MainDAO: Database created successfully or is already created.");
	        createProjectTable();
	        System.out.println("MainDAO: Project table created successfully or is already created.");
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
	
	public ArrayList<ProjectBean> fetchAllProjects() {
		String sql = "SELECT * from ProjectTable";
		ArrayList<ProjectBean> list = new ArrayList<ProjectBean>();
		
		try {
			Statement statement = conn.createStatement();
			ResultSet set = statement.executeQuery(sql);
			
			while (set.next()) {
				String dateString = set.getString("startingDate");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate localDate = LocalDate.parse(dateString, formatter);
				list.add(new ProjectBean(set.getString("name"),	localDate, set.getString("description")));
			}

		
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
