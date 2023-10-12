package application.java_beans;

import java.io.Serializable;
import java.time.LocalDate;

public class ProjectBean implements Serializable {

	private String projectName;
	private String projectDescription;
	private LocalDate startingDate;
	
	public ProjectBean(String projectName, LocalDate startingDate, String projectDescription) {
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.startingDate = startingDate;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public String getProjectDescription() {
		return projectDescription;
	}
	
	public LocalDate getStartingDate() {
		return startingDate;
	}
	
	public void setProjectName(String newName) {
		projectName = newName;
	}
	
	public void setProjectDescription(String newDescription) {
		projectDescription = newDescription;
	}
	
	public void setStartingDate(LocalDate newDate) {
		startingDate = newDate;
	}
}
