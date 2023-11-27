package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import application.data_access_objects.EditTicketDAO;
import application.java_beans.TicketBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * This is the Controller class for the Project page
 */
public class EditTicketController {
	/** Instance of the ProjectDAO class for data accessing */
	private EditTicketDAO dataAccess;

	/** HBox for displaying the new project page */
	@FXML HBox editTicket;

	/** Text field for entering project name */
	@FXML TextField nameField;
	
	/** BText field for entering project description */
	@FXML TextArea descriptionField;
	
	TicketBean ticket = null;
	
	/**
	 * Initializes ProjectDAO and accesses database with all projects
	 */
	@FXML public void initialize() {
		dataAccess = new EditTicketDAO();
	}
	
	public void initAll(TicketBean ticket) {
		this.ticket = ticket;
		nameField.setText(ticket.getTicketName());
		descriptionField.setText(ticket.getTicketDescription());
	}
	
	/**
	 * Displays the new project page when the "New Project" button is pressed.
	 */
	@FXML public void showNewTicketPage() {
		
		URL url = getClass().getClassLoader().getResource("view/NewTicket.fxml");
		
		try {
			// Stage is fetched
			Stage stage = (Stage) editTicket.getScene().getWindow(); 
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
			// Stage is fetched
			Stage stage = (Stage) editTicket.getScene().getWindow(); 
			HBox pane1 = (HBox)FXMLLoader.load(url);
			Scene scene = new Scene(pane1);
			// Set scene
			stage.setScene(scene);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Stores the information entered in the TextFields and DatePicker into a ProjectBean and send 
	 * it to the database
	 */
	@FXML public void submit() {
		String name = nameField.getText();
		String description = descriptionField.getText();
		int id = ticket.getTicketID();
		
		TicketBean bean = new TicketBean(name, description, ticket.getProjectFromTicket(), id);
		dataAccess.editTicketRecord(bean);
		showTicketPage();
		
	}
}