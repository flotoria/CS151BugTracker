package application.controller;

import application.data_access_objects.CommentDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class CommentController {
	/** Instance of the ProjectDAO class for data accessing */
	private CommentDAO dataAccess;

	/** HBox for displaying the new project page */
	@FXML HBox newComment;
	
	/** BText field for entering project description */
	@FXML TextArea descriptionField;
	
	/**
	 * Initializes ProjectDAO and accesses database with all projects
	 */
	@FXML public void initialize() {
		dataAccess = new CommentDAO();
	}
}
