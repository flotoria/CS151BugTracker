package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import application.data_access_objects.TicketDAO;
import application.java_beans.ProjectBean;
import application.java_beans.TicketBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TicketController {
	
	@FXML HBox newTicket;
	@FXML TextField nameField;
	@FXML TextArea descriptionField;
	@FXML ComboBox<String> dropdown;
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
	 * Displays the homepage when the "Back" button is pressed
	 */
	@FXML public void populateDropdown() {
		ArrayList<ProjectBean> listOfProjects = dataAccess.fetchAllProjects();
		ArrayList<String> projectNameList = new ArrayList<String>();
		for (ProjectBean p : listOfProjects) {
			projectNameList.add(p.getProjectName());
		}
		
		
		Collections.sort(projectNameList);
		ObservableList<String> data = FXCollections.observableArrayList(projectNameList);
		
		dropdown.setItems(data);
	}
	
	public void submit() {
		String projectName = dropdown.getValue();
		String ticketName = nameField.getText();
		String ticketDescription = nameField.getText();
		int id = dataAccess.fetchProjectIDByName(projectName);
		ProjectBean proj = dataAccess.fetchProjectByID(id);
		dataAccess.createTicketRecord(new TicketBean(ticketName, ticketDescription, proj));
		
		showHomepage();
	}
}
