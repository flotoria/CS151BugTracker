package application.java_beans;

import java.time.LocalDateTime;

public class CommentBean {
	/** Description of the comment */
	private String commentText;
	private LocalDateTime timestamp;
	private TicketBean ticket;
	
	/**
	 * Creates a new CommentBean
	 * @param commentText	the description of the new comment
	 */
	public CommentBean(String commentText, LocalDateTime timestamp, TicketBean ticket) {
		this.commentText = commentText;
		this.timestamp = timestamp;
		this.ticket = ticket;
	}

	/**
	 * Returns the comment text as a String
	 * @return	the comment text
	 */
	public String getCommentText() {
		return commentText;
	}
	
	/**
	 * Returns the comment starting date as a LocalDate
	 * @return	the comment starting date
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public TicketBean getTicket() {
		return ticket;
	}
	
	/**
	 * Sets a new text for the comment
	 * @param newText	the new comment text
	 */
	public void setCommentText(String newText) {
		commentText = newText;
	}

}
