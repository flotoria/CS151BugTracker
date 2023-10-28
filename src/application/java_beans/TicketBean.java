package application.java_beans;

/**
 * Creates and modifies a TicketBean that is used to communicate with the database
 */
public class TicketBean {
	/** Name of the ticket */
	private String ticketName;

	/** Description of the ticket */
	private String ticketDescription;

	/** Associated project with the ticket */
	private ProjectBean project;

	/** Unique ID of the ticket */
	private int ticketID;
	
	/**
	 * Creates a new TicketBean
	 * @param ticketName			the name of the new ticket
	 * @param ticketDescription		the description of the new ticket
	 * @param project				the associated project
	 */
	public TicketBean(String ticketName, String ticketDescription, ProjectBean project) {
		this.ticketName = ticketName;
		this.ticketDescription = ticketDescription;
		this.project = project;
		this.ticketID = -1;
	}
	
	/**
	 * Creates a new TicketBean
	 * @param ticketName			the name of the new ticket
	 * @param ticketDescription		the description of the new ticket
	 * @param project				the associated project
	 * @param ticketID				the unique ticketID
	 */
	public TicketBean(String ticketName, String ticketDescription, ProjectBean project, int ticketID) {
		this.ticketName = ticketName;
		this.ticketDescription = ticketDescription;
		this.project = project;
		this.ticketID = ticketID;
	}
	
	/**
	 * Returns the ticket name as a String
	 * @return	the ticket name
	 */
	public String getTicketName() {
		return ticketName;
	}

	/**
	 * Returns the ticket unique ID
	 * @return	the ticket ID
	 */
	public int getTicketID() {
		return ticketID;
	}
	
	/**
	 * Returns the ticket description as a String
	 * @return	the ticket description
	 */
	public String getTicketDescription() {
		return ticketDescription;
	}
	
	/**
	 * Returns the project associated with the ticket
	 * @return	the associated project
	 */
	public ProjectBean getProjectFromTicket() {
		return project;
	}
	
	/**
	 * Sets a new name for the ticket
	 * @param newName	the new ticket name
	 */
	public void setTicketName(String newName) {
		ticketName = newName;
	}
	
	/**
	 * Sets a new description for the ticket
	 * @param newDescription	the new ticket description
	 */
	public void setTicketDescription(String newDescription) {
		ticketDescription = newDescription;
	}

	/**
	 * Sets a project for the ticket
	 * @param newProject	the project to be associated with the ticket
	 */
	public void setProject(ProjectBean newProject) {
		project = newProject;
	}
}
