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

public class ProjectController {
	
	private ProjectDAO dataAccess;
	@FXML HBox newProject;

	@FXML TextField nameField;
	@FXML DatePicker datePicker;
	@FXML TextArea descriptionField;
	
	@FXML public void initialize() {
		datePicker.setValue(LocalDate.now());
		dataAccess = new ProjectDAO();
	}
	
	/*
	 * If the back button is pressed, the function to show the homepage is run.
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
	
	@FXML public void submit() {
		String name = nameField.getText();
		LocalDate date = datePicker.getValue();
		String description = descriptionField.getText();
		
		ProjectBean bean = new ProjectBean(name, date, description);
		dataAccess.createProjectRecord(bean);
		showHomepage();
		
	}
}
