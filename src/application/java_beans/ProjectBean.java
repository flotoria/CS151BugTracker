package application.java_beans;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Creates and modifies a ProjectBean that is used to communicate with the database
 */
public class ProjectBean implements Serializable {
	/** Name of the project */
	private String projectName;

	/** Description of the project */
	private String projectDescription;

	/** Starting date of the project */
	private LocalDate startingDate;
	
	private int projectID;
	
	/**
	 * Creates a new ProjectBean
	 * @param projectName			the name of the new project
	 * @param startingDate			the starting date of the new project
	 * @param projectDescription	the description of the new project
	 */
	public ProjectBean(String projectName, LocalDate startingDate, String projectDescription) {
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.startingDate = startingDate;
	}
	
	public ProjectBean(String projectName, LocalDate startingDate, String projectDescription, int projectID) {
		this.projectName = projectName;
		this.projectDescription = projectDescription;
		this.startingDate = startingDate;
		this.projectID = projectID;
	}
	
	
	/**
	 * Returns the project name as a String
	 * @return	the project name
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Returns the project description as a String
	 * @return	the project description
	 */
	public String getProjectDescription() {
		return projectDescription;
	}
	
	/**
	 * Returns the project starting date as a LocalDate
	 * @return	the project starting date
	 */
	public LocalDate getStartingDate() {
		return startingDate;
	}
	
	public int getProjectID() {
		return projectID;
	}
	
	/**
	 * Sets a new name for the project
	 * @param newName	the new project name
	 */
	public void setProjectName(String newName) {
		projectName = newName;
	}
	
	/**
	 * Sets a new description for the project
	 * @param newDescription	the new project description
	 */
	public void setProjectDescription(String newDescription) {
		projectDescription = newDescription;
	}

	/**
	 * Sets a new date for the project
	 * @param newDate	the new project date
	 */
	public void setStartingDate(LocalDate newDate) {
		startingDate = newDate;
	}
}
