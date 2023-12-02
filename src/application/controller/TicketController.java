package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import application.data_access_objects.TicketDAO;
import application.java_beans.CommentBean;
import application.java_beans.ProjectBean;
import application.java_beans.TicketBean;
import application.string_converter.ProjectBeanStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * This is the Controller class for the Ticket page
 */
public class TicketController {
	/** HBox for displaying the new ticket page */
	@FXML HBox newTicket;
	
	/** Text field for entering ticket name */
	@FXML TextField nameField;
	
	/** Text field for entering ticket description */
	@FXML TextArea descriptionField;
	
	/** Dropdown selection box for existing projects */
	@FXML ComboBox<ProjectBean> dropdown;
	

	/** Instance of the TicketDAO class for data accessing */
	private TicketDAO dataAccess;
	
	
	public void initialize() {
		dataAccess = new TicketDAO();
		populateDropdown();
	}
	
	/**
	 * Displays the homepage when the "Back" button is pressed
	 */
	@FXML public void showHomepage() {
		URL url = getClass().getClassLoader().getResource("view/Main.fxml");
		
		try {
			// Stage is fetched
			Stage stage = (Stage) newTicket.getScene().getWindow(); 
			HBox pane1 = (HBox)FXMLLoader.load(url);
			Scene scene = new Scene(pane1);
			// Set scene
			stage.setScene(scene);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Populates the selection box dropdown with existing projects
	 */
	@FXML public void populateDropdown() {
		ArrayList<ProjectBean> listOfProjects = dataAccess.fetchAllProjects();

		listOfProjects.sort(Comparator.comparing(ProjectBean::getProjectName));
		ObservableList<ProjectBean> list = FXCollections.observableArrayList(listOfProjects);
		
		
		dropdown.setItems(list);
		dropdown.setConverter(new ProjectBeanStringConverter());
		dropdown.setCellFactory(cb -> new ListCell<ProjectBean>() {
			@Override
			public void updateItem(ProjectBean project, boolean empty) {
				super.updateItem(project, empty) ;
		        setText(empty ? null : project.getProjectName());
			}
		});
	}
	
	/**
	 * Stores the information entered in the TextField into a TicketBean and send 
	 * it to the database
	 */
	public void submit() {
		ProjectBean project = dropdown.getValue();
		String name = nameField.getText();
		String description = descriptionField.getText();
		if (project != null && !name.equals("")) {
			dataAccess.createTicketRecord(new TicketBean(name, description, project));
			showHomepage();
		}
	}
	
}
