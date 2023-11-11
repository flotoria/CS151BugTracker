package application.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import application.data_access_objects.ProjectDAO;
import application.data_access_objects.SearchTicketDAO;
import application.java_beans.CommentBean;
import application.java_beans.ProjectBean;
import application.java_beans.TicketBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SearchTicketController {
	
	private String query; 
	
	
	private SearchTicketDAO dataAccess;
	
	/** HBox for displaying the search ticket page */
	@FXML HBox searchTicket;

	
	/** List of all ticket beans */
	@FXML ListView<TicketBean> ticketList;
	
	/**
	 * Initializes SearchTicketDAO and accesses database with all projects
	 */
	@FXML public void initialize() {
		dataAccess = new SearchTicketDAO();
	}
	
	/**
	 * Takes the name, description, and project name of the comment's associated ticket and presents it
	 * @param ticket	the ticket to be associated with this comment
	 */
	public void initAll(String s) {
        query = s;
        searchTickets();
    }
	
	@FXML public void searchTickets() {
		
		ArrayList<TicketBean> list = dataAccess.fetchTicketsByName(query);
		
		ArrayList<ProjectBean> projectList = dataAccess.fetchCertainProjects(query);
		
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
		        setText(empty ? null : ticket.getProjectFromTicket().getProjectName() + ": " + ticket.getTicketName());
			}
			
		}		
		);
		
	
	}
	
	/**
	 * Displays the ticket's comments when clicked on
	 */
	@FXML public void clickTicket() {
		URL url = getClass().getClassLoader().getResource("view/NewComment.fxml");
		
		if (ticketList.getSelectionModel().getSelectedItem() != null) {
			try {
				// Stage is fetched
				Stage stage = (Stage) searchTicket.getScene().getWindow(); 
				
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
	 * Displays the homepage when the "Back" button is pressed
	 */
	@FXML public void showHomepage() {
		URL url = getClass().getClassLoader().getResource("view/Main.fxml");
		
		try {
			// Stage is fetched
			Stage stage = (Stage) searchTicket.getScene().getWindow(); 
			HBox pane1 = (HBox)FXMLLoader.load(url);
			Scene scene = new Scene(pane1);
			// Set scene
			stage.setScene(scene);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
