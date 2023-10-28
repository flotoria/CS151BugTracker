package application.java_beans;

import java.time.LocalDate;

public class CommentBean {
	/** Description of the comment */
	private String commentDescription;
	
	/**
	 * Creates a new CommentBean
	 * @param commentDescription	the description of the new comment
	 */
	public CommentBean(String commentDescription) {
		this.commentDescription = commentDescription;
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
		return LocalDate.now();
	}
	
	/**
	 * Sets a new description for the comment
	 * @param newDescription	the new comment description
	 */
	public void setCommentDescription(String newDescription) {
		commentDescription = newDescription;
	}

}
