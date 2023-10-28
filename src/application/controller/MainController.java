package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import application.data_access_objects.MainDAO;
import application.java_beans.ProjectBean;
import application.java_beans.TicketBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainController {
	/** HBox for displaying homepage */
	@FXML HBox mainBox;
	
	/** List of all project names */
	@FXML ListView<ProjectBean> projectList;
	
	@FXML ListView<TicketBean> ticketList;
	
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

		ObservableList<ProjectBean> data = FXCollections.observableArrayList(list);
		projectList.setItems(data);
		projectList.setCellFactory(lv -> new ListCell<ProjectBean>() {
			@Override
			public void updateItem(ProjectBean project, boolean empty) {
				super.updateItem(project, empty) ;
		        setText(empty ? null : project.getProjectName());
			}
			
		}		
		);
	}
	
	@FXML public void clickProject() { 
		ProjectBean selectedProject = projectList.getSelectionModel().getSelectedItem();
		if (selectedProject != null) {
			ArrayList<TicketBean> list = dataAccess.fetchTicketsByProjectID(selectedProject.getProjectID());
			ObservableList<TicketBean> data = FXCollections.observableArrayList(list);
			ticketList.setItems(data);
			ticketList.setCellFactory(lv -> new ListCell<TicketBean>() {
				@Override
				public void updateItem(TicketBean ticket, boolean empty) {
					super.updateItem(ticket, empty) ;
			        setText(empty ? null : ticket.getTicketName());
				}
				
			}		
			);
			
		}
	} 

}
