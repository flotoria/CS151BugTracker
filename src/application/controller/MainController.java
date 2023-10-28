package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import application.data_access_objects.MainDAO;
import application.java_beans.ProjectBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainController {
	/** HBox for displaying homepage */
	@FXML HBox mainBox;
	
	/** List of all project names */
	@FXML ListView<String> projectList;
	
	@FXML ListView<String> ticketList;
	
	/** Instance of the MainDAO class for data accessing */
	private MainDAO dataAccess;
	
	/**
	 * Initializes MainDAO and accesses database with all projects
	 */
	public void initialize() {
		dataAccess = new MainDAO();
		showAllProjects();
		
	}
	
	/**
	 * Displays the new project page when the "New Project" button is pressed.
	 */
	@FXML public void showNewProjectPage() {
		
		URL url = getClass().getClassLoader().getResource("view/NewProject.fxml");
		
		try {
			// Stage is fetched
			Stage stage = (Stage) mainBox.getScene().getWindow(); 
			HBox pane1 = (HBox)FXMLLoader.load(url);
			Scene scene = new Scene(pane1);
			// Set scene
			stage.setScene(scene);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Displays the new ticket page when the "New Project" button is pressed.
	 */
	@FXML public void showNewTicketPage() {
		
		URL url = getClass().getClassLoader().getResource("view/NewTicket.fxml");
		
		try {
			// Stage is fetched
			Stage stage = (Stage) mainBox.getScene().getWindow(); 
			HBox pane1 = (HBox)FXMLLoader.load(url);
			Scene scene = new Scene(pane1);
			// Set scene
			stage.setScene(scene);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Takes the collection of projects on the database and displays them
	 */
	@FXML public void showAllProjects() {
		
		ArrayList<ProjectBean> list = dataAccess.fetchAllProjects();
		
		ArrayList<String> names = new ArrayList<String>();
		for (ProjectBean p : list) {
			names.add(p.getProjectName());
		}
		
		ObservableList<String> data = FXCollections.observableArrayList(names);
		projectList.setItems(data);
	}
	
	@FXML public void click() { 
		String selectedButtonName = projectList.getSelectionModel().getSelectedItem();
		int id = dataAccess.fetchProjectIDByName(selectedButtonName);
		ArrayList<String> str = dataAccess.fetchTicketNamesByProjectID(id);
		System.out.println(str);
		ObservableList<String> data = FXCollections.observableArrayList(str);
		ticketList.setItems(data);

	}

}
