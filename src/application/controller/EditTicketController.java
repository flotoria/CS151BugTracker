package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import application.data_access_objects.EditTicketDAO;
import application.java_beans.TicketBean;
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
	 * Displays the homepage when the "Back" button is pressed
	 */
	@FXML public void showTicketPage() {
		URL url = getClass().getClassLoader().getResource("view/NewComment.fxml");
		
		try {
			
			TicketBean editedTicket = dataAccess.fetchTicketByTicketID(ticket.getTicketID());
			// Stage is fetched
			Stage stage = (Stage) editTicket.getScene().getWindow(); 
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
	
	/**
	 * Stores the information entered in the TextFields and DatePicker into a ProjectBean and send 
	 * it to the database
	 */
	@FXML public void submit() {
		String name = nameField.getText();
		if(name.equals("")) name = ticket.getTicketName();
		String description = descriptionField.getText();
		int id = ticket.getTicketID();
		
		TicketBean bean = new TicketBean(name, description, ticket.getProjectFromTicket(), id);
		dataAccess.editTicketRecord(bean);
		showTicketPage();
		
	}
}