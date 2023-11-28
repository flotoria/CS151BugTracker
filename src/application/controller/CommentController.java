package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import application.data_access_objects.CommentDAO;
import application.java_beans.CommentBean;
import application.java_beans.ProjectBean;
import application.java_beans.TicketBean;
import application.controller.EditCommentController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * This is the Controller class for the Comment page
 */
public class CommentController {
	/** Instance of the CommentDAO class for data accessing */
	private CommentDAO dataAccess;

	/** HBox for displaying the new comment page */
	@FXML HBox newComment;
	
	/** Text area for comment description */
	@FXML TextArea commentField;
	
	/** Text flow for comment description */
	@FXML ListView<CommentBean> commentListView;

	/** Text flow for comment timestamp */
	@FXML TextField timestampField;
	
	/** Label for comment name */
	@FXML Label nameLabel;
	
	/** Label for desciption */
	@FXML Label descriptionLabel;
	
	/** Label for project name */
	@FXML Label projectLabel;
	
	@FXML Button edit;
	
	@FXML Button delete;
	
	private TicketBean ticket;
	
	/**
	 * Initializes CommentDAO and accesses database with all projects
	 */
	@FXML public void initialize() {
		dataAccess = new CommentDAO();
	    LocalDateTime timestamp = LocalDateTime.now();
	    timestampField.setText(Timestamp.valueOf(timestamp).toString());
	}
	
	/**
	 * Takes the name, description, and project name of the comment's associated ticket and presents it
	 * @param ticket	the ticket to be associated with this comment
	 */
	public void initAll(TicketBean ticket) {
	    this.ticket = ticket;
	    nameLabel.setText(ticket.getTicketName());
	    descriptionLabel.setText(ticket.getTicketDescription());
	    projectLabel.setText(ticket.getProjectFromTicket().getProjectName());

	    ArrayList<CommentBean> commentList = dataAccess.fetchCommentsByTicket(ticket);

	    // Clear the existing items in the ListView
	    commentListView.getItems().clear();

	    // Update the ListView with the new comment format
	    commentListView.getItems().addAll(commentList);
    }
	

	/**
	 * Edits chosen ticket
	 */
	@FXML public void showEditTicketPage() { 
		URL url = getClass().getClassLoader().getResource("view/EditTicket.fxml");
		
		try {
			
			// Stage is fetched
			Stage stage = (Stage) newComment.getScene().getWindow(); 
			
			FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            EditTicketController controller = loader.getController();
            Scene scene = new Scene(root);
            
            controller.initAll(ticket);
			// Set scene
			stage.setScene(scene);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes chosen ticket
	 */
	@FXML public void deleteTicket() { 
		dataAccess.deleteTicketByID(ticket.getTicketID());
		showHomepage();
	}
	
	/**
	 * Stores the information entered in the comment description and and the current date into a 
	 * CommentBean and send it to the database
	 */
	@FXML public void submit() {
	    String text = commentField.getText();
	    LocalDateTime timestamp = LocalDateTime.now();
	    timestampField.setText(Timestamp.valueOf(timestamp).toString());

	    CommentBean newComment = new CommentBean(text, timestamp, ticket);
	    dataAccess.createCommentRecord(newComment);

	    // Update the ListView with the new comment
	    commentListView.getItems().add(newComment);
		
	}
	
	/**
	 * Displays the homepage when the "Back" button is pressed
	 */
	@FXML public void showHomepage() {
		URL url = getClass().getClassLoader().getResource("view/Main.fxml");
		
		try {
			// Stage is fetched
			Stage stage = (Stage) newComment.getScene().getWindow(); 
			HBox pane1 = (HBox)FXMLLoader.load(url);
			Scene scene = new Scene(pane1);
			// Set scene
			stage.setScene(scene);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Displays the edit comment when clicked on (BUGGED)
	 */
	@FXML public void clickComment() {
		URL url = getClass().getClassLoader().getResource("view/EditComment.fxml");
		
		System.out.println("Clicked (1)");
		
		if (commentListView.getSelectionModel().getSelectedItem() != null) {
			try {
				// Stage is fetched
				Stage stage = (Stage) newComment.getScene().getWindow(); 
				
				FXMLLoader loader = new FXMLLoader(url);
	            Parent root = loader.load();
	            EditCommentController controller = loader.getController();
	            CommentBean selectedComment = commentListView.getSelectionModel().getSelectedItem();
	            if (selectedComment != null) {
	                controller.initAll(selectedComment);
	            }
	            
				Scene scene = new Scene(root);
				// Set scene
				stage.setScene(scene);
				System.out.println("Clicked (2)");
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
