package application.java_beans;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Creates and modifies a CommentBean that is used to communicate with the database
 */
public class CommentBean {
	/** Description of the comment */
	private String commentText;
	
	/** The date and time of the comment's creation */
	private LocalDateTime timestamp;
	
	/** The ticket associated with this comment */
	private TicketBean ticket;
	
	private int commentID;
	
	/**
	 * Creates a new CommentBean
	 * @param commentText	the description of the new comment
	 * @param timestamp		the time of the comment's creation
	 * @param ticket		the ticket associated with this comment
	 */
	public CommentBean(String commentText, LocalDateTime timestamp, TicketBean ticket) {
		this.commentText = commentText;
		this.timestamp = timestamp;
		this.ticket = ticket;
		this.commentID = -1;
	}
	
	/**
	 * Creates a new CommentBean
	 * @param commentText	the description of the new comment
	 * @param timestamp		the time of the comment's creation
	 * @param ticket		the ticket associated with this comment
	 * @param commentID		the unique comment ID
	 */
	public CommentBean(String commentText, LocalDateTime timestamp, TicketBean ticket, int commentID) {
		this.commentText = commentText;
		this.timestamp = timestamp;
		this.ticket = ticket;
		this.commentID = commentID; 
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
	
	/**
	 * Returns the ticket associated with this comment
	 * @return	the comment associated with this ticket
	 */
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
	
	@Override
	public String toString() {
	    return Timestamp.valueOf(timestamp).toString() + ": " + commentText;
	}

	public int getCommentID() {
		return commentID;
	}

}
