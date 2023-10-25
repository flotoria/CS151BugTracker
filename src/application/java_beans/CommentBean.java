package application.java_beans;

import java.time.LocalDate;

public class CommentBean {
	/** Description of the comment */
	private String commentDescription;

	/** Starting date of the comment */
	private LocalDate startingDate;
	
	/**
	 * Creates a new CommentBean
	 * @param startingDate			the starting date of the new comment
	 * @param commentDescription	the description of the new comment
	 */
	public CommentBean(LocalDate startingDate, String commentDescription) {
		this.commentDescription = commentDescription;
		this.startingDate = startingDate;
	}

	/**
	 * Returns the comment description as a String
	 * @return	the comment description
	 */
	public String getCommentDescription() {
		return commentDescription;
	}
	
	/**
	 * Returns the comment starting date as a LocalDate
	 * @return	the comment starting date
	 */
	public LocalDate getStartingDate() {
		return startingDate;
	}
	
	/**
	 * Sets a new description for the comment
	 * @param newDescription	the new comment description
	 */
	public void setCommentDescription(String newDescription) {
		commentDescription = newDescription;
	}

	/**
	 * Sets a new date for the comment
	 * @param newDate	the new comment date
	 */
	public void setStartingDate(LocalDate newDate) {
		startingDate = newDate;
	}

}
