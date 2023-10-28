package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import application.data_access_objects.TicketDAO;
import application.java_beans.ProjectBean;
import application.java_beans.TicketBean;
import application.string_converter.ProjectBeanStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TicketController {
	
	@FXML HBox newTicket;
	@FXML TextField nameField;
	@FXML TextArea descriptionField;
	@FXML ComboBox<ProjectBean> dropdown;
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
	
	public void submit() {
		ProjectBean project = dropdown.getValue();
		String name = nameField.getText();
		String description = descriptionField.getText();
		dataAccess.createTicketRecord(new TicketBean(name, description, project));
		
		showHomepage();
	}
}
