package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import application.data_access_objects.CommentDAO;
import application.java_beans.CommentBean;
import application.java_beans.ProjectBean;
import application.java_beans.TicketBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class CommentController {
	/** Instance of the ProjectDAO class for data accessing */
	private CommentDAO dataAccess;

	/** HBox for displaying the new project page */
	@FXML HBox newComment;
	
	/** BText field for entering project description */
	@FXML TextArea commentField;
	
	@FXML TextFlow commentArea;

	@FXML TextField timestampField;
	
	@FXML Label nameLabel;
	
	@FXML Label descriptionLabel;
	
	@FXML Label projectLabel;
	private TicketBean ticket;
	
	/**
	 * Initializes CommentDAO and accesses database with all projects
	 */
	@FXML public void initialize() {
		dataAccess = new CommentDAO();
		LocalDateTime timestamp = LocalDateTime.now();
		timestampField.setText(Timestamp.valueOf(timestamp).toString());
		
	}
	
	public void initAll(TicketBean ticket) {
        this.ticket = ticket;
        nameLabel.setText(ticket.getTicketName());
        descriptionLabel.setText(ticket.getTicketDescription());
        projectLabel.setText(ticket.getProjectFromTicket().getProjectName());
        
        ArrayList<CommentBean> commentList = dataAccess.fetchCommentsByTicket(ticket);
		
		for (CommentBean c : commentList) {
			commentArea.getChildren().add(new Text(Timestamp.valueOf(c.getTimestamp()).toString() + ": "+ c.getCommentText() + "\n"));
		}
    }
	
	@FXML public void submit() {
		String text = commentField.getText();
		LocalDateTime timestamp = LocalDateTime.now();
		timestampField.setText(Timestamp.valueOf(timestamp).toString());
		
		
		dataAccess.createCommentRecord(new CommentBean(text, timestamp, ticket));
		commentArea.getChildren().add(new Text(Timestamp.valueOf(timestamp).toString() + ": "+ text + "\n"));
		
	}
	
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
}