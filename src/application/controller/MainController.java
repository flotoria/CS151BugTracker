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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * This is the Controller class for the Main page
 */
public class MainController {
	/** HBox for displaying homepage */
	@FXML HBox mainBox;
	
	/** List of all project beans */
	@FXML ListView<ProjectBean> projectList;

	/** List of all ticket beans */
	@FXML ListView<TicketBean> ticketList;

	/** Label for project name */
	@FXML Label nameLabel;

	/** Label for project description */
	@FXML Label descriptionLabel;

	/** Label for project date */
	@FXML Label dateLabel;
	
	/** Label for project search bar inputs */
	@FXML TextField projectSearchField;
	
	/** Label for ticket search bar inputs */
	@FXML TextField ticketSearchField;
	
	/** Instance of the MainDAO class for data accessing */
	private MainDAO dataAccess;
	
	/** Button for project editing */
	@FXML Button edit;
	
	/** Button for project deleting */
	@FXML Button delete;
	
	/**
	 * Initializes MainDAO and accesses database with all projects
	 */
	public void initialize() {
		dataAccess = new MainDAO();
		edit.setVisible(false);
		delete.setVisible(false);
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
	 * Displays the edit project page when the "Edit" button is pressed.
	 */
	@FXML public void showEditProjectPage() {
		
		URL url = getClass().getClassLoader().getResource("view/EditProject.fxml");
		
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
	
	/**
	 * Displays the project's tickets when clicked on
	 */
	@FXML public void clickProject() { 
		ProjectBean selectedProject = projectList.getSelectionModel().getSelectedItem();
		if (selectedProject != null) {
			edit.setVisible(true);
			delete.setVisible(true);
			
			nameLabel.setText(selectedProject.getProjectName());
			descriptionLabel.setText(selectedProject.getProjectDescription());
			dateLabel.setText(selectedProject.getStartingDate().toString());
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
	
	/**
	 * Deletes chosen project
	 */
	@FXML public void deleteProject() { 
		ProjectBean selectedProject = projectList.getSelectionModel().getSelectedItem();
		int id = selectedProject.getProjectID();
		dataAccess.deleteProjectByID(id);

		showAllProjects();
	}
	
	/**
	 * Searches for all projects matching the search name
	 */
	@FXML public void searchProjects() {
		String name = projectSearchField.getText();
		
		ArrayList<ProjectBean> list = dataAccess.fetchCertainProjects(name);

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
	
	/**
	 * Searches for all tickets matching the search name
	 */
	@FXML public void searchTickets() {
		String name = ticketSearchField.getText();
		
		if (name != "") {
		//	nameLabel.setText(selectedProject.getProjectName());
		//	descriptionLabel.setText(selectedProject.getProjectDescription());
		//	dateLabel.setText(selectedProject.getStartingDate().toString());
			ArrayList<TicketBean> list = dataAccess.fetchTicketsByName(name);
			
			ArrayList<ProjectBean> projectList = dataAccess.fetchCertainProjects(name);
			
			for (ProjectBean project: projectList) {
				ArrayList<TicketBean> ticketListFromProject = dataAccess.fetchTicketsByProjectID(project.getProjectID());
				for (TicketBean aTicket: ticketListFromProject) {
					list.add(aTicket);
				}
			}

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
		else {
			ticketList.setItems(null);
		}
	}
	
	/**
	 * Displays the ticket's comments when clicked on
	 */
	@FXML public void clickTicket() {
		URL url = getClass().getClassLoader().getResource("view/NewComment.fxml");
		
		if (ticketList.getSelectionModel().getSelectedItem() != null) {
			try {
				// Stage is fetched
				Stage stage = (Stage) mainBox.getScene().getWindow(); 
				
				FXMLLoader loader = new FXMLLoader(url);
	            Parent root = loader.load();
	            CommentController controller = loader.getController();
	            controller.initAll(ticketList.getSelectionModel().getSelectedItem());
	            
				Scene scene = new Scene(root);
				// Set scene
				stage.setScene(scene);
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Displays the ticket's comments when clicked on
	 */
	@FXML public void clickSearchTicket() {
		
		String name = ticketSearchField.getText();
		
		URL url = getClass().getClassLoader().getResource("view/QueriedTickets.fxml");
		
		try {
			// Stage is fetched
			Stage stage = (Stage) mainBox.getScene().getWindow(); 
			
			FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            SearchTicketController controller = loader.getController();
            controller.initAll(name);
            
			Scene scene = new Scene(root);
			// Set scene
			stage.setScene(scene);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	


}
