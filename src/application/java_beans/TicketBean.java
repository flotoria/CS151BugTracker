package application.java_beans;

import java.time.LocalDate;

public class TicketBean {
	/** Name of the ticket */
	private String ticketName;

	/** Description of the ticket */
	private String ticketDescription;

	/** Starting date of the ticket */
	private LocalDate startingDate;
	
	/**
	 * Creates a new TicketBean
	 * @param ticketName			the name of the new ticket
	 * @param startingDate			the starting date of the new ticket
	 * @param ticketDescription	the description of the new ticket
	 */
	public TicketBean(String ticketName, LocalDate startingDate, String ticketDescription) {
		this.ticketName = ticketName;
		this.ticketDescription = ticketDescription;
		this.startingDate = startingDate;
	}
	
	/**
	 * Returns the ticket name as a String
	 * @return	the ticket name
	 */
	public String getTicketName() {
		return ticketName;
	}

	/**
	 * Returns the ticket description as a String
	 * @return	the ticket description
	 */
	public String getTicketDescription() {
		return ticketDescription;
	}
	
	/**
	 * Returns the ticket starting date as a LocalDate
	 * @return	the ticket starting date
	 */
	public LocalDate getStartingDate() {
		return startingDate;
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
	 * Sets a new date for the ticket
	 * @param newDate	the new ticket date
	 */
	public void setStartingDate(LocalDate newDate) {
		startingDate = newDate;
	}
}
