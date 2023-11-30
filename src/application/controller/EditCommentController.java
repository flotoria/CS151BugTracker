package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import application.data_access_objects.EditCommentDAO;
import application.data_access_objects.EditTicketDAO;
import application.java_beans.CommentBean;
import application.java_beans.TicketBean;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * This is the Controller class for the Project page
 */
public class EditCommentController {
	/** Instance of the ProjectDAO class for data accessing */
	private EditCommentDAO dataAccess;

	/** HBox for displaying the new comment page */
	@FXML HBox editComment;

	/** Text field for entering comment text */
	@FXML TextArea commentText;
	
	
	CommentBean comment = null;
	
	/**
	 * Initializes ProjectDAO and accesses database with all projects
	 */
	@FXML public void initialize() {
		dataAccess = new EditCommentDAO();
	}

	public void initAll(CommentBean comment) {
		this.comment = comment;
		commentText.setText(comment.getCommentText());
	}
	
	
	
	/**
	 * Displays the new project page when the "New Project" button is pressed.
	 */
	@FXML public void showNewTicketPage() {
		
		URL url = getClass().getClassLoader().getResource("view/NewTicket.fxml");
		
		try {
			// Stage is fetched
			Stage stage = (Stage) editComment.getScene().getWindow(); 
			HBox pane1 = (HBox)FXMLLoader.load(url);
			Scene scene = new Scene(pane1);
			// Set scene
			stage.setScene(scene);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the homepage when the "Back" button is pressed
	 */
	@FXML public void showTicketPage() {
		URL url = getClass().getClassLoader().getResource("view/NewComment.fxml");
		
		try {
			
			TicketBean ticket = comment.getTicket();
			TicketBean editedTicket = dataAccess.fetchTicketByTicketID(ticket.getTicketID());
			// Stage is fetched
			Stage stage = (Stage) editComment.getScene().getWindow(); 
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = loader.load();
	        CommentController controller = loader.getController();
	        
	        
	        controller.initAll(editedTicket);
	        
			// Set scene
	        Scene scene = new Scene(root);
			stage.setScene(scene);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void delete() {
		dataAccess.deleteCommentByID(comment.getCommentID());
		showTicketPage();
	}
	
	
	/**
	 * Stores the information entered in the TextFields and DatePicker into a ProjectBean and send 
	 * it to the database
	 */
	@FXML public void submit() {
		String text= commentText.getText();
		int id = comment.getCommentID();
		LocalDateTime timestamp = comment.getTimestamp();
		if (!text.equals(comment.getCommentText())) {
			timestamp = LocalDateTime.now();
			System.out.println("t");
		}
		CommentBean bean = new CommentBean(text, timestamp, comment.getTicket(), id);
		dataAccess.editCommentRecord(bean);
		showTicketPage();
		
	}
}
