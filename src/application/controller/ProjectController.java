package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import application.data_access_objects.ProjectDAO;
import application.java_beans.ProjectBean;
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
public class ProjectController {
	/** Instance of the ProjectDAO class for data accessing */
	private ProjectDAO dataAccess;

	/** HBox for displaying the new project page */
	@FXML HBox newProject;

	/** Text field for entering project name */
	@FXML TextField nameField;
	
	/** Button for entering date */
	@FXML DatePicker datePicker;
	
	/** BText field for entering project description */
	@FXML TextArea descriptionField;
	
	/**
	 * Initializes ProjectDAO and accesses database with all projects
	 */
	@FXML public void initialize() {
		datePicker.setValue(LocalDate.now());
		dataAccess = new ProjectDAO();
	}
	
	/**
	 * Displays the new project page when the "New Project" button is pressed.
	 */
	@FXML public void showNewTicketPage() {
		
		URL url = getClass().getClassLoader().getResource("view/NewTicket.fxml");
		
		try {
			// Stage is fetched
			Stage stage = (Stage) newProject.getScene().getWindow(); 
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
	@FXML public void showHomepage() {
		URL url = getClass().getClassLoader().getResource("view/Main.fxml");
		
		try {
			// Stage is fetched
			Stage stage = (Stage) newProject.getScene().getWindow(); 
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
		LocalDate date = datePicker.getValue();
		String description = descriptionField.getText();
		
		ProjectBean bean = new ProjectBean(name, date, description);
		dataAccess.createProjectRecord(bean);
		showHomepage();
		
	}
}
